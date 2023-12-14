pipeline {
    agent any

    environment {
        dockerCredentials               = 'dockerCredentials'
        registry                        = 'islmm/islemnaffeti-5sim3-g1-projet4-spring-angular'
        dockerImage                     = ''
        sonarToken                      = credentials('sonarToken')

    }

    stages {
        stage('Clean Projects') {
            steps {

                    sh "mvn clean"

            }
        }
        stage('Building project') {
            steps {

                    sh "mvn validate"
                    sh "mvn compile"

            }
        }
        stage('Docker Image') {
            steps {

                        script {
                            dockerImage = docker.build registry + ":1.0.0"

                }
            }
        }
        stage('Docker Push to hub') {
            steps {

                    script {
                        docker.withRegistry('', dockerCredentials) { dockerImage.push() }

                }
            }
        }
        stage("Docker Compose") {
            steps {
                sh "docker compose up -d"
            }
        }
        stage('Test the code') {
            steps {

                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh 'mvn test -Dspring.profiles.active=test -Dtest="EventServiceImplTest"'

                }
            }
        }
        stage('Jacoco') {
             steps {

                    sh "mvn jacoco:report"

             }
        }
        stage('SONAR') {
            steps {

                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "mvn sonar:sonar -Dsonar.token=$sonarToken"

                }
            }
        }
        stage('Nexus') {
            steps {

                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh "mvn deploy -DskipTests"

                }
            }
        }



    }
}