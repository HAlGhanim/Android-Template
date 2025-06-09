#!/bin/bash

# Usage: ./rename-template.sh yourprojectname com.yourcompany.yourapp

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <new_project_name> <new_package_name>"
  echo "Example: $0 mybankapp com.example.mybank"
  exit 1
fi

NEW_PROJECT_NAME=$1
NEW_PACKAGE_NAME=$2

# Convert package name to path
PACKAGE_PATH=$(echo $NEW_PACKAGE_NAME | tr '.' '/')

# Replace text in all project files
echo "🔁 Replacing 'androidtemplate' → $NEW_PROJECT_NAME"
echo "🔁 Replacing 'com.example.androidtemplate' → $NEW_PACKAGE_NAME"

find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.gradle.kts" -o -name "*.gradle" -o -name "*.md" \) \
  -exec sed -i "" "s/androidtemplate/$NEW_PROJECT_NAME/g" {} +
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.gradle.kts" -o -name "*.gradle" -o -name "*.md" \) \
  -exec sed -i "" "s/com\.example\.androidtemplate/$NEW_PACKAGE_NAME/g" {} +

# Rename source directories
echo "📁 Moving source files to new package path: $PACKAGE_PATH"

SRC_ROOT="./app/src/main/java"
OLD_PATH="$SRC_ROOT/com/example/androidtemplate"
NEW_PATH="$SRC_ROOT/$PACKAGE_PATH"

mkdir -p "$NEW_PATH"
mv "$OLD_PATH"/* "$NEW_PATH"/ 2>/dev/null
rm -rf "$SRC_ROOT/com/example/androidtemplate"

# Repeat for androidTest and test
for TEST_TYPE in androidTest test; do
  SRC="./app/src/$TEST_TYPE/java"
  OLD="$SRC/com/example/androidtemplate"
  NEW="$SRC/$PACKAGE_PATH"
  mkdir -p "$NEW"
  mv "$OLD"/* "$NEW"/ 2>/dev/null
  rm -rf "$OLD"
done

echo "✅ Renaming complete."
echo "📌 Open Android Studio:"
echo "1. File → Sync Project with Gradle Files"
echo "2. File → Invalidate Caches / Restart"
