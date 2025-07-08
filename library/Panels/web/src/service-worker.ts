/// <reference types="@sveltejs/kit" />
/// <reference lib="webworker" />
declare let self: ServiceWorkerGlobalScope

import { build, files, version } from "$service-worker"

const CACHE = `panels-${version}`

const ASSETS = [...build, ...files]

self.addEventListener("install", (event) => {
  async function addFilesToCache() {
    const cache = await caches.open(CACHE)
    await cache.addAll(ASSETS)
  }

  event.waitUntil(addFilesToCache())
})

self.addEventListener("activate", (event) => {
  async function deleteOldCaches() {
    for (const key of await caches.keys()) {
      if (key !== CACHE) await caches.delete(key)
    }
  }

  event.waitUntil(deleteOldCaches())
})

self.addEventListener("fetch", (event) => {
  if (event.request.method !== "GET") return

  async function respond() {
    const url = new URL(event.request.url)
    const cache = await caches.open(CACHE)

    if (ASSETS.includes(url.pathname)) {
      const response = await cache.match(url.pathname)

      if (response) {
        return response
      }
    }

    try {
      const response = await fetch(event.request)

      if (!(response instanceof Response)) {
        throw new Error("invalid response from fetch")
      }

      const isNotExtension = url.protocol == "http:"
      const isSuccess = response.status == 200

      if (isNotExtension && isSuccess) {
        cache.put(event.request, response.clone())
      }

      return response
    } catch (err) {
      const response = await cache.match(event.request)

      if (response) {
        return response
      }

      throw err
    }
  }

  event.respondWith(respond())
})

self.addEventListener("message", (event) => {
  if (event.data && event.data.type == "SKIP_WAITING") {
    self.skipWaiting()
  }
})
