#!/bin/bash

set -e

set -o allexport
source SETTINGS
set +o allexport

############################################################
pushd $SRC_HOME
activator clean dist
popd

rm -rf target
mkdir -p target/lib

unzip $SRC_HOME/target/universal/$DISTRIB_NAME.zip -d $SRC_HOME/target/universal/
#cp -rf $SRC_HOME/target/universal/$DISTRIB_NAME/lib/content-server* target/lib/.
cp -rf $SRC_HOME/target/universal/$DISTRIB_NAME/lib/* target/lib/.
cp -rf $SRC_HOME/target/universal/$DISTRIB_NAME/bin target/.
cp -rf $SRC_HOME/target/universal/$DISTRIB_NAME/conf target/.

rm -rf $SRC_HOME/target/universal/$DISTRIB_NAME

sed -e "s/{SET_PORT}/$PORT/g" entrypoint.sh > target/entrypoint.sh
############################################################

source $DISTRIB_HOME/bin/task_definition/publish_new_revision.sh

source $DISTRIB_HOME/bin/aws/update_service.sh
