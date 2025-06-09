#!/bin/bash

# Usage: ./rename-template.sh mybankapp com.example.mybank

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <new_project_name> <new_package_name>"
  echo "Example: $0 mybankapp com.example.mybank"
  exit 1
fi

# Arguments
NEW_PROJECT_NAME=$1
NEW_PACKAGE_NAME=$2
PACKAGE_PATH=$(echo "$NEW_PACKAGE_NAME" | tr '.' '/')

# Convert project name to PascalCase (capitalize first letter of each word split by non-alpha)
NEW_PROJECT_PASCAL=$(echo "$NEW_PROJECT_NAME" | sed -E 's/(^|-)([a-z])/\U\2/g')

echo "🔁 Replacing 'androidtemplate' → $NEW_PROJECT_NAME"
echo "🔁 Replacing 'AndroidTemplate' → $NEW_PROJECT_PASCAL"
echo "🔁 Replacing 'com.example.androidtemplate' → $NEW_PACKAGE_NAME"

# 1. Replace lowercase: androidtemplate → mybankapp
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.gradle.kts" -o -name "*.gradle" -o -name "*.md" \) \
  -exec sed -i "s/androidtemplate/$NEW_PROJECT_NAME/g" {} +

# 2. Replace PascalCase: AndroidTemplate → MyBankApp
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.gradle.kts" -o -name "*.gradle" -o -name "*.md" \) \
  -exec sed -i "s/AndroidTemplate/$NEW_PROJECT_PASCAL/g" {} +

# 3. Replace package: com.example.androidtemplate → com.example.mybank
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.gradle.kts" -o -name "*.gradle" -o -name "*.md" \) \
  -exec sed -i "s/com\.example\.androidtemplate/$NEW_PACKAGE_NAME/g" {} +

# 4. Move source files
echo "📁 Moving source files to: $PACKAGE_PATH"
SRC_MAIN="./app/src/main/java"
OLD_PATH="$SRC_MAIN/com/example/androidtemplate"
NEW_PATH="$SRC_MAIN/$PACKAGE_PATH"
mkdir -p "$NEW_PATH"
if [ -d "$OLD_PATH" ]; then
  mv "$OLD_PATH"/* "$NEW_PATH"/ 2>/dev/null
  rm -rf "$SRC_MAIN/com/example/androidtemplate"
fi

# 5. Move test files
for TEST_TYPE in androidTest test; do
  SRC="./app/src/$TEST_TYPE/java"
  OLD="$SRC/com/example/androidtemplate"
  NEW="$SRC/$PACKAGE_PATH"
  mkdir -p "$NEW"
  if [ -d "$OLD" ]; then
    mv "$OLD"/* "$NEW"/ 2>/dev/null
    rm -rf "$OLD"
  fi
done

echo "✅ Renaming complete!"
echo "📌 Final Steps:"
echo "1. Open Android Studio"
echo "2. File > Sync Project with Gradle Files"
echo "3. File > Invalidate Caches / Restart"
