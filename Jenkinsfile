pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                sh 'export APP_PATH=/opt/servers/jenkins/workspace/vibot-youtube '
                        + '&& YOUTUBE_SECRET_TEST_FILE=/home/jllado/dev/vibot/.youtube_client_secret.test.json '
                        + '&& docker-compose -f docker-compose.build.yml up --build --exit-code-from vibot-youtube-build'
            }
        }
        stage ('Deploy') {
            steps {
                sh 'export YOUTUBE_SECRET_FILE=/home/jllado/dev/vibot/.youtube_client_secret.json && docker-compose up -d --build'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/**/*.xml'
        }
        success {
            script {
                sh "curl -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"${env.JOB_NAME} SUCCESS\"}' http://172.17.0.1:50000/notification"
            }
        }
        unstable {
            script {
                sh "curl -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"${env.JOB_NAME} UNSTABLE\"}' http://172.17.0.1:50000/notification"
            }
        }
        failure {
            script {
                sh "curl -X POST -H \"Content-Type: application/json\" -d '{\"text\": \"${env.JOB_NAME} FAILURE\"}' http://172.17.0.1:50000/notification"
            }
        }
    }
}
