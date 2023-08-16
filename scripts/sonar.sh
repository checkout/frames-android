export SONAR_SCANNER_VERSION=4.7.0.2747
export SONAR_SCANNER_HOME=$HOME/.sonar/sonar-scanner-$SONAR_SCANNER_VERSION-linux
curl --create-dirs -sSLo $HOME/.sonar/sonar-scanner.zip https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-$SONAR_SCANNER_VERSION-linux.zip
unzip -o $HOME/.sonar/sonar-scanner.zip -d $HOME/.sonar/
export PATH=$SONAR_SCANNER_HOME/bin:$PATH
export SONAR_SCANNER_OPTS="-server"

sonar-scanner \
  -Dsonar.projectKey=checkout_frames-android_AYn5jEtzfXz2nF6wDbxu \
  -Dsonar.sources=. \
  -Dsonar.coverage.jacoco.xmlReportPaths=**/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml \
  -Dsonar.host.url=https://sonarqube-ext.mgmt.ckotech.co \
  -Dsonar.exclusions="readme-docs/**,example_app_frames/**,docs/**,code_quality_tools/**,buildSrc/**,app/**,**/*.java,**/src/test/**,**/src/androidTest/**" \
