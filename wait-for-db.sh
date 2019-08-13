#!/bin/bash

echo "********************************************************"
echo "Waiting for the database server to start on port 5432"
echo "********************************************************"
while ! `nc -z postgres 5432`; do sleep 3s; done
echo "******** Database Server has started "