description = ""

kotlin.sourceSets {
    val jvmMain by getting {
        dependencies {
            api(project(":ktor-shared:ktor-websockets"))
            api(project(":ktor-shared:ktor-websocket-serialization"))
        }

        val jvmTest by getting {
            dependencies {
                api(project(":ktor-server:ktor-server-plugins:ktor-server-content-negotiation"))
                api(project(":ktor-server:ktor-server-jetty"))
                api(project(":ktor-server:ktor-server-netty"))
                api(project(":ktor-server:ktor-server-tomcat"))
                api(project(":ktor-server:ktor-server-cio"))
                api(project(":ktor-server:ktor-server-core", configuration = "testOutput"))
            }
        }
    }
}
