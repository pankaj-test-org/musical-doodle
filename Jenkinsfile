pipeline {
    agent any
    environment {
        FAIL_THIS_BUILD = 'false'
    }
    stages {
        stage('Stage-0') {
            steps {
                echo 'Stage-0: deterministic mock load (sleep 3)'
                sh 'sleep 3'
            }
        }

        stage('Stage-1') {
            parallel {
                stage('Stage-1.1') {
                    steps {
                        echo "Stage-1.1: first step"
                        sh 'sleep 3'
                        echo "Stage-1.1: Execution completed!!!!!!!!!"
                    }
                }

                stage('Stage-1.2') {
                    stages {
                        stage('1.2.1') {
                            steps {
                                echo "Stage-1.2.1: deterministic mock load (sleep 9)"
                                sh 'sleep 9'
                            }
                        }
                        stage('1.2.2') {
                            steps {
                                echo "Stage-1.2.2: deterministic mock load (sleep 9)"
                                sh 'sleep 9'
                            }
                        }
                    }
                }
            }
        }

        stage('Stage - 2- Parallel-Block') {
            parallel {
                stage('Stage-2.1 - Parallel Stage') {
                    steps {
                        echo "Stage-2.1: deterministic mock load (sleep 5)"
                        sh 'sleep 5'
                    }
                }
                stage('Stage 2.2 - Test - Parallel stage') {
                    steps {
                        echo 'Running unit tests...'
                        // if you want the tests to have a fixed duration replace with a deterministic command
                        sh 'sleep 2'
                        echo 'End of mockLoads'
                        // Add actual unit test commands (mvn test) if you want them here
                    }
                }
            }
        }

        stage("Stage - 3 - Registering") {
            steps {
                echo 'Next step will register the metadata'
                registerBuildArtifactMetadata(
                        name: "build-artifact",
                        version: "1.0.0",
                        type: "jar",
                        url: "https://pankajy-dev.me/job/mbp/job/main/1/artifact/target/app.jar/*view*/",
                        digest: "6f637064707039346163663237383938",
                        label: "prod"
                )
            }
        }

        stage('Stage - 4 - Archive artifacts') {
            steps {
                echo 'Compiling the project...'
                sh 'mkdir -p target && echo "dummy jar content" > target/app.jar'
                archiveArtifacts artifacts: 'target/*.jar'
                echo 'Artifact generated: target/app.jar'
            }
        }

        stage('Stage - 5 - clean compile') {
            steps {
                echo 'Starting build stage...'
                sh 'mvn -B clean compile'
                echo 'Build stage completed.'
            }
        }

        stage('Stage - 6 - mvn test run') {
            steps {
                echo 'Starting test stage...'
                sh 'mvn -B test'
                echo 'Test stage completed.'
            }
        }

        stage('Stage - 7 - Archive Test Results') {
            steps {
                echo 'Archiving test results...'
                junit '**/target/surefire-reports/*.xml'
                echo 'Test results archived.'
            }
        }

        stage('Security Scan') {
            steps {
                // Pretend to trigger a Security Scan
                sh "echo 'Security scan result' > scan.sarif && ls -l scan.sarif"
                // Prepare the security scan for sending
                registerSecurityScan artifacts: "scan*", format: "sarif"
                registerSecurityScan artifacts: "my-scan*", format: "snyk", scanner: "sonarqube"
            }
        }

    }
    post {
        always {
            echo "Pipeline finished with status: ${currentBuild.currentResult}"
        }
    }
}
