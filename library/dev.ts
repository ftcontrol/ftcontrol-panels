import { globalDev } from "ftc-panels/cli"

let folders = []
folders = ["Lights"]
// folders = null
globalDev(__dirname, folders).catch((err) => {
  console.error("Unhandled error in build process:", err)
})
