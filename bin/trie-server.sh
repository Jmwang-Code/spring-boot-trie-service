#!/bin/bash


BASE_DIR=$(cd "$(dirname "$0")/.."; pwd -P)

echo "working dir ${BASE_DIR}"

cd "${BASE_DIR}"

CLASS_PATH="${BASE_DIR}/lib/*"

START_CLASS="com.cn.jmw.RegressionSearchTree"

#java -server -Xms2G -Xmx2G  -Dspring.profiles.active=config -Dfile.encoding=UTF-8 -cp "${CLASS_PATH}" com.cn.jmw.RegressionSearchTree

datart_status(){
    #result=`ps -ef | awk '/RegressionSearchTree/ && !/awk/{print $2}' | wc -l`
    result=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RegressionSearchTree' | awk {'print $2'} | wc -l`

    if [[ $result -eq 0 ]]; then
        return 0
    else
        return 1
    fi
    }

trie_start(){
    source ~/.bashrc
    datart_status >/dev/null 2>&1
    if [[ $? -eq 0 ]]; then

        nohup  java -server -Xms256M -Xmx256M -Dspring.profiles.active=config -Dfile.encoding=UTF-8 -cp "${CLASS_PATH}" "${START_CLASS}" &

    else
        echo ""
        #PID=`ps -ef | awk '/RegressionSearchTree/ && !/awk/{print $2}'`
        PID=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RegressionSearchTree' | awk {'print $2'}`
        echo "Datart is Running Now..... PID is ${PID} "
    fi
}


trie_stop(){
    datart_status >/dev/null 2>&1
    if [[ $? -eq 0 ]]; then
        echo ""
        echo "Datart is not Running....."
        echo ""
    else
         #ps -ef | awk '/DatartServerApplication/ && !/awk/{print $2}'| xargs kill -9
         ps -ef | grep -v grep | grep "$BASE_DIR/lib" | grep 'RegressionSearchTree' | awk {'print $2'} | xargs kill -9

    fi
}


case $1 in
    start )
        echo ""
        echo "RegressionSearchTree Starting........... "
        echo ""
        trie_start
    ;;

    stop )
        echo ""

        echo "RegressionSearchTree Stopping.......... "

        echo ""
        trie_stop
    ;;

    restart )
        echo "RegressionSearchTree is Restarting.......... "
        trie_stop
        echo ""
        trie_start
        echo "RegressionSearchTree is Starting.......... "

    ;;

    status )
        datart_status>/dev/null 2>&1
        if [[ $? -eq 0 ]]; then
            echo ""
            echo "RegressionSearchTree is not Running......"
            echo ""
        else
            echo ""
            #PID=`ps -ef | awk '/RegressionSearchTree/ && !/awk/{print $2}'`
            PID=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RegressionSearchTree' | awk {'print $2'}`
            echo "RegressionSearchTree is Running..... PID is ${PID}"
            echo ""
        fi
     ;;

    * )
        echo "Usage: trie-server.sh (start|stop|status|restart)"

esac
