# Jenkins Shared Library Collection

This repo contains bunch of Shared Library Classes and Global variables(methods) that can be used in CI/CD pipelines.
Repo will be updated as new functionalities added.

## Slack & Opsgenie Notification/Alerting

You can use Slack & Opsgenie methods for notification and alerting.


### Requirements:
 - Jenkins HTTP Request Plugin.

### Example: 
```Pipeline
@Library('jenkins-shared-lib') _
pipeline {
    agent any
    stages {
        stage ('Example') {
            steps {
                script {
                    // echod is not correct command which returns non-zero return code.
                    echod "hello"
                }
            }
        }
    }
    post {
        failure {
            script{
                slack(message: "Failed Jenkins Job: <${BUILD_URL}|Click here> for details!")
                opsgenie(message: "Jenkins: Opsgenie Alert", description: "Failed Jenkins Job: <${BUILD_URL}|Click here> for details!")
            }
        }
    }
}
```
