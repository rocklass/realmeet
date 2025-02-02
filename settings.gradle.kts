pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RealMeet"
include(":app")
include(":core:camera")
include(":core:design-system")
include(":core:navigation")
include(":core:network")
include(":features:capture:capture-data")
include(":features:capture:capture-domain")
include(":features:capture:capture-ui")
include(":features:home:home-ui")
include(":features:share:share-data")
include(":features:share:share-domain")
include(":features:share:share-ui")