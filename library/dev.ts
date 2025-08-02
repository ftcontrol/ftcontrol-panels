import { globalDev } from "ftc-panels/cli"

var folders = []
folders = ["Configurables"]

globalDev(__dirname, folders).catch((err) => {
  console.error("Unhandled error in build process:", err)
})
