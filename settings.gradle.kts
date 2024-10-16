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

rootProject.name = "Movies Compose"
include(":app")
include(":core:model")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:designsystem")
include(":feature:home")
include(":feature:details")
include(":feature:mylist")
include(":feature:search")
