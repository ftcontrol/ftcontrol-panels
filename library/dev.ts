import { globalDev } from "ftc-panels/cli"

globalDev(__dirname).catch((err) => {
  console.error("Unhandled error in build process:", err)
})
