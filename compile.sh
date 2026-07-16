#!/bin/bash
# compile.sh - Build and deploy script for AJP project
# Usage: ./compile.sh        - Build WAR and deploy to Tomcat
#        ./compile.sh deploy  - Same as above
#        ./compile.sh build   - Only build WAR
#        ./compile.sh runjdbc Q1  - Build and run a JDBC program (e.g. classWork.Q1_JDBCConnectivity)
#        ./compile.sh tomcat-start  - Start Tomcat server
#        ./compile.sh tomcat-stop   - Stop Tomcat server
#        ./compile.sh help    - Show this message

set -e

TOMCAT_HOME="$HOME/opt/tomcat"
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
WAR_FILE="$PROJECT_DIR/target/AJP.war"

build() {
    echo "=== Building AJP project ==="
    cd "$PROJECT_DIR"
    mvn clean package -f pom.xml
    echo "=== Build complete ==="
}

deploy() {
    build
    echo "=== Deploying to Tomcat ==="
    cp "$WAR_FILE" "$TOMCAT_HOME/webapps/"
    echo "WAR deployed to $TOMCAT_HOME/webapps/"
}

tomcat_start() {
    echo "=== Starting Tomcat ==="
    $TOMCAT_HOME/bin/startup.sh
}

tomcat_stop() {
    echo "=== Stopping Tomcat ==="
    $TOMCAT_HOME/bin/shutdown.sh
}

run_jdbc() {
    if [ -z "$1" ]; then
        echo "Usage: $0 runjdbc <class-name>"
        echo "Example: $0 runjdbc classWork.Q1_JDBCConnectivity"
        exit 1
    fi
    cd "$PROJECT_DIR"
    mvn compile -q -f pom.xml
    classpath="$PROJECT_DIR/target/classes:$PROJECT_DIR/lib/mysql-connector-j-9.7.0.jar"
    echo "=== Running $1 ==="
    java -cp "$classpath" "$1"
}

case "${1:-deploy}" in
    build)
        build
        ;;
    deploy)
        deploy
        ;;
    runjdbc)
        shift
        run_jdbc "$@"
        ;;
    tomcat-start)
        tomcat_start
        ;;
    tomcat-stop)
        tomcat_stop
        ;;
    help|--help|-h)
        sed -n '2,14p' "$0"
        ;;
    *)
        echo "Unknown option: $1"
        echo "Usage: ./compile.sh <build|deploy|runjdbc|tomcat-start|tomcat-stop>"
        exit 1
        ;;
esac
