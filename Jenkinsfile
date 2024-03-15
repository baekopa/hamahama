pipeline {
    agent any

    stages {
        stage("CI/CD start") {
            steps {
                script {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()

                    mattermostSend (
                        color: '#D0E0E3',
                        icon: "https://jenkins.io/images/logos/jenkins/jenkins.png",
                        // message: "배포 출발 합니다 🛫 "
                        message: "배포 출발 합니다 🛫 \n${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)"
                    )
                }
            }
        }
    }

    post {
        success {
            script {
                mattermostSend (
                    color: 'good',
                    icon: "https://jenkins.io/images/logos/jenkins/jenkins.png",
                    message: "🎉 배포 성공 🎉"
                )
            }
        }
        failure {
            script {
                mattermostSend (
                    color: 'danger',
                    icon: "https://jenkins.io/images/logos/jenkins/jenkins.png",
                    message: "실패라니 😰"
                )
            }
        }
    }
}
