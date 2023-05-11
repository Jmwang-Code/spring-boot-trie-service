#!/bin/bash

# Datart
# <p>
# Copyright 2021
# <p>
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# <p>
# http://www.apache.org/licenses/LICENSE-2.0
# <p>
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

BASE_DIR=$(cd "$(dirname "$0")/.."; pwd -P)

echo "working dir ${BASE_DIR}"

cd "${BASE_DIR}"

CLASS_PATH="${BASE_DIR}/lib/*"

START_CLASS="com.cn.jmw.RPC"

#java -server -Xms2G -Xmx2G  -Dspring.profiles.active=config -Dfile.encoding=UTF-8 -cp "${CLASS_PATH}" com.cn.jmw.RPC

datart_status(){
    #result=`ps -ef | awk '/RPC/ && !/awk/{print $2}' | wc -l`
    result=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RPC' | awk {'print $2'} | wc -l`

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
        #PID=`ps -ef | awk '/RPC/ && !/awk/{print $2}'`
        PID=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RPC' | awk {'print $2'}`
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
         ps -ef | grep -v grep | grep "$BASE_DIR/lib" | grep 'RPC' | awk {'print $2'} | xargs kill -9

    fi
}


case $1 in
    start )
        echo ""
        echo "RPC Starting........... "
        echo ""
        trie_start
    ;;

    stop )
        echo ""

        echo "RPC Stopping.......... "

        echo ""
        trie_stop
    ;;

    restart )
        echo "RPC is Restarting.......... "
        trie_stop
        echo ""
        trie_start
        echo "RPC is Starting.......... "

    ;;

    status )
        datart_status>/dev/null 2>&1
        if [[ $? -eq 0 ]]; then
            echo ""
            echo "RPC is not Running......"
            echo ""
        else
            echo ""
            #PID=`ps -ef | awk '/RPC/ && !/awk/{print $2}'`
            PID=`ps -ef | grep -v grep | grep "${BASE_DIR}/lib" | grep 'RPC' | awk {'print $2'}`
            echo "RPC is Running..... PID is ${PID}"
            echo ""
        fi
     ;;

    * )
        echo "Usage: trie-server.sh (start|stop|status|restart)"

esac
