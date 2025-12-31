pipeline {
    agent any

    environment {
        REPORT_DIR = "${WORKSPACE}/reports/build-${BUILD_NUMBER}"
    }

    stages {

        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    sh """
                    mkdir -p ${REPORT_DIR}
                    mvn test -Dsurefire.reportDirectory=${REPORT_DIR}
                    """
                }
            }
        }

        stage('Publish Report') {
            steps {
                publishHTML(target: [
                    reportDir: "reports/build-${BUILD_NUMBER}",
                    reportFiles: 'index.html',
                    reportName: "Test Report - Build ${BUILD_NUMBER}",
                    keepAll: true
                ])
            }
        }
    }
}
