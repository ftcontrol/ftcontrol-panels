plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.bylazar"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("svelteAssetsPlugin") {
            id = "com.bylazar.svelte-assets"
            implementationClass = "com.bylazar.SvelteAssetsPlugin"
        }
    }
}
