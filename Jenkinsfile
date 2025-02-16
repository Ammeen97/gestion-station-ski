pipeline {
    agent any

    environment {
        // Define environment variables
        GIT_REPO = 'https://github.com/Ammeen97/gestion-station-ski.git'
        BRANCH = 'main'
        SONAR_PROJECT_KEY = 'gestion-station-ski'
        SONAR_HOST_URL = 'http://10.0.2.15:9000' // Replace with your SonarQube server URL
        SONAR_TOKEN = credentials('amine-sonar-token') // Store your SonarQube token in Jenkins credentials
    }

    stages {
        stage('Git Checkout') {
            steps {
                // Checkout the code from GitHub
                git branch: "${BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Maven Clean') {
            steps {
                // Run Maven clean
                sh 'mvn clean'
            }
        }
        stage('Maven Compile'){
            steps{
                sh 'mvn compile'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server configured in Jenkins
                    sh 'mvn sonar:sonar -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.login=${SONAR_TOKEN}'
                }
            }
        }
        stage('Deploy') {
            steps {
                // Deploy the application
                echo 'Deploying application...'
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            // Clean up workspace after the build
            cleanWs()
        }
    }
}