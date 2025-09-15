pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compiling the project...'
                sh 'mkdir -p target && echo "dummy jar content" > target/app.jar'
                archiveArtifacts artifacts: 'target/*.jar'
                echo 'Artifact generated: target/app.jar'
            }
        }
    }
}