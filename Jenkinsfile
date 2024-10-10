pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'anil135/java-app-microservice'
        DOCKER_CREDENTIALS_ID = 'docker-repo-credentials' // Docker credentials stored in Jenkins
        DATABASE_URL = 'postgresql://postgres:postgres@localhost:5432/test'
        DB_CONTAINER_NAME = 'jenkins_db'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the repository
                git branch: 'main', url: 'https://github.com/anil135/java-app.git'
            }
        }

        stage('Install JDK 17'){
            steps{
                // install jdk 17

                sh 'sudo apt update'
                sh 'sudo apt install -y openjdk-17-jdk '

            }
        }
        stage('Install maven'){
            stepa{
                // Install maven to build
                sh 'sudo apt install -y maven'
            }
        }
        stage('Build') {
                    steps {
                        // Build the Java application using Maven or Gradle
                        sh 'mvn clean package'  // For Maven
                        // sh './gradlew build'    // For Gradle
                    }
                }


        stage('Build Docker Image') {
            steps {
                // Build the Docker image for the microservice
                sh "docker build -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
            }
        }


        stage('Push Docker Image') {
            steps {
                // Push the Docker image to the repository
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKER_IMAGE}:${BUILD_NUMBER}
                    '''
                }
            }
        }

        stage('Cleanup') {
            steps {
                // Remove PostgreSQL container
                sh "docker stop ${DB_CONTAINER_NAME} && docker rm ${DB_CONTAINER_NAME}"
            }
        }
    }

    post {
        always {
            // Cleanup Docker resources if necessary
            sh 'docker system prune -f'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the logs for errors.'
        }
    }
}