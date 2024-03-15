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
                        message: "배포 출발 합니다 🛫 \n${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)"
                    )
                }
            }
        }

        stage("Clone Repository") {
            steps {
                echo '클론 시작'
                git branch: 'release', credentialsId: 'gitlablogin', url: 'https://lab.ssafy.com/s10-ai-speech-sub2/S10P22D105.git'
                echo '클론 끝'
            }
        }

        stage("secret.yml download") {
            steps {
                withCredentials([file(credentialsId: 'secret-db', variable: 'dbConfigFile')]) {
                    script {
                        sh 'sudo cp $dbConfigFile ./Backend/src/main/resources/application-db.yml'
                    }
                }

                withCredentials([file(credentialsId: 'secret-jwt', variable: 'jwtConfigFile')]) {
                    script {
                        sh 'sudo cp $jwtConfigFile ./Backend/src/main/resources/application-jwt.yml'
                    }
                }

                withCredentials([file(credentialsId: 'secret-oauth', variable: 'oauthConfigFile')]) {
                    script {
                        sh 'sudo cp $oauthConfigFile ./Backend/src/main/resources/application-oauth.yml'
                    }
                }

            }
        }

        stage("BE Build") {
            steps {
                echo '백엔드 빌드 시작!'

                dir("./Backend") {
                    // sh "chmod +x ./gradlew"
                    // sh "./gradlew clean build"
                }

                echo '백엔드 빌드 완료!'
            }
        }

        stage('Build BE JAR to Docker Image') {
            steps {
                echo '백엔드 도커 이미지 빌드 시작!'
                dir("./Backend") {
                    // 빌드된 JAR 파일을 Docker 이미지로 빌드
                    sh "docker build -t oiatmil/d105-be:latest ."
                }
                echo '백엔드 도커 이미지 빌드 완료!'
            }
        }

        stage('Push to Docker Hub-BE') {
            steps {
                echo '백엔드 도커 이미지를 Docker Hub에 푸시 시작!'
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                }
                dir("./Backend") {
                    sh "docker push oiatmil/d105-be:latest"
                }
                echo '백엔드 도커 이미지를 Docker Hub에 푸시 완료!'
            }
        }

        stage('Deploy to EC2-BE') {
            steps {
                echo '백엔드 EC2에 배포 시작!'
                // 여기에서는 SSH 플러그인이나 SSH 스크립트를 사용하여 EC2로 연결하고 Docker 컨테이너 실행
                
                sh "docker rm -f backend"
                sh "docker rmi oiatmil/d105-be:latest"
                sh "docker image prune -f"
                sh "docker pull oiatmil/d105-be:latest && docker run -d -p 8080:8080 --name backend oiatmil/d105-be:latest"
                
                echo '백엔드 EC2에 배포 완료!'
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
