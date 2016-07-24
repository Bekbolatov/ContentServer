#!/bin/bash


ln -s /EFS/distrib/lib/* /deployment/lib/

#register
PORT={SET_PORT}
THIS_HOST=$(curl http://169.254.169.254/latest/meta-data/local-ipv4)
touch /EFS/run/services/contentserver/$THIS_HOST:$PORT


exec $@
