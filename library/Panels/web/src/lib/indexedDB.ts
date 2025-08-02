const DB_NAME = "PanelsDB"
const STORE_NAME = "PluginData"
const DB_VERSION = 1

type MyStoreSchema = {
  [key: string]: string
}

type StoreKey = keyof MyStoreSchema
type StoreValue = MyStoreSchema[StoreKey]

export async function openDB(): Promise<IDBDatabase> {
  return new Promise((resolve, reject) => {
    const request = indexedDB.open(DB_NAME, DB_VERSION)

    request.onupgradeneeded = () => {
      const db = request.result
      if (!db.objectStoreNames.contains(STORE_NAME)) {
        db.createObjectStore(STORE_NAME)
      }
    }

    request.onsuccess = () => resolve(request.result)
    request.onerror = () => reject(request.error)
  })
}

export async function storeValue<K extends StoreKey>(
  key: K,
  value: MyStoreSchema[K]
): Promise<true> {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readwrite")
    const store = tx.objectStore(STORE_NAME)
    const request = store.put(value, key as string)
    request.onsuccess = () => resolve(true)
    request.onerror = () => reject(request.error)
  })
}

export async function readValue<K extends StoreKey>(
  key: K
): Promise<MyStoreSchema[K] | null> {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readonly")
    const store = tx.objectStore(STORE_NAME)
    const request = store.get(key as string)
    request.onsuccess = () =>
      resolve((request.result ?? null) as MyStoreSchema[K] | null)
    request.onerror = () => reject(request.error)
  })
}

export async function deleteValue<K extends StoreKey>(key: K): Promise<true> {
  const db = await openDB()
  return new Promise((resolve, reject) => {
    const tx = db.transaction(STORE_NAME, "readwrite")
    const store = tx.objectStore(STORE_NAME)
    const request = store.delete(key as string)
    request.onsuccess = () => resolve(true)
    request.onerror = () => reject(request.error)
  })
}
