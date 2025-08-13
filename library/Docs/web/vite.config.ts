import { defineConfig } from 'vite'
import path from 'node:path'

export default defineConfig({
    resolve: {
        alias: {
            '@examples': path.resolve(__dirname, '../../../examples/TeamCode/src/main/java/org/firstinspires/ftc/teamcode')
        }
    },
})
