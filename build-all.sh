#!/bin/bash

services=(
  "sharedlib"
  "auth-service"
  "bid-service"
  "gateway-service"
  "notification-service"
  "payment-service"
  "discovery-server"
  "auction-service"
  "user-service"
)

for service in "${services[@]}"; do
  echo "Building $service..."
  (
    cd "$service" || exit
    ./mvnw clean package -DskipTests
  )
done

echo "All services built successfully!"