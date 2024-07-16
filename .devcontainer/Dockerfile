# Use a Java base image
FROM mcr.microsoft.com/devcontainers/java:0-17

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /workspace

# Copy the project files
COPY . /workspace