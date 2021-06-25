# CONTRIBUTING.md

This document describes the contribution guidelines for this project.

## Commit convention

This projects follows the *gitmoji* commit message convention (see [https://gitmoji.dev/](https://gitmoji.dev/))

Commits message are build this way:

```
<emoji> : <message>

<details>

resolves #<issue-number>
```

The `<emoji>` can be an emoji code (such as <em>:sparkles:</em>) or a unicode character (such as ✨).

## How to add a feature

* create a branch for the feature (eg: `feature/my-nice-feature`)
```shell
git checkout -b feature/my-nice-feature
```
* code your feature, and create commits for each subject/scope
* update the `pom.xml` to the next minor or major version (eg: 1.0.1 -> 1.1.0)
* commit the change to the `pom.xml` eg:
```shell
git add pom.xml && git commit -m "🔖 : bump version to 1.1.0"
```
* generate the CHANGELOG.md for the new version
```shell
npx gitmoji-changelog --preset maven --group-similar-commits true
```
* commit the CHANGELOG.md in the previous commit
```shell
git add CHANGELOG.md && git commit --amend --no-edit
```
* when done, push your branch, and open a merge request, with the pull request template.
```shell
git push --set-upstream origin feature/my-nice-feature
```

### Merging the feature

* merge the feature branch in merge-commit mode
```shell
git checkout main
git merge --no-ff feature/my-nice-feature
```
* create a tag for the feature, on the main branch, on the merge-commit that you just created
```shell
git tag v1.1.0
git push --tags
```
* enjoy & follow the automated build of the tag

## How to add a fix

* create a branch for the fix (eg: `hotfix/my-nice-fix`)
```shell
git checkout -b hotfix/my-nice-fix
```
* code your fix, and create commits for each subject
* update the `pom.xml` to the next fix version (eg: 1.0.1 -> 1.0.2) 
* commit the change to the `pom.xml` eg:
```shell
git add pom.xml && git commit -m "🔖 : bump version to 1.0.2"
```
* generate the CHANGELOG.md for the new version
```shell
npx gitmoji-changelog --preset maven --group-similar-commits true
```
* commit the CHANGELOG.md in the previous commit
```shell
git add CHANGELOG.md && git commit --amend --no-edit
```
* when done, push your branch, and open a merge request, with the pull request template.
```shell
git push --set-upstream origin hotfix/my-nice-fix
```

### Merging the fix

* merge the fix branch in merge-commit mode
```shell
git checkout main
git merge --no-ff hotfix/my-nice-fix
```
* create a tag for the fix, on the main branch, on the merge-commit that you just created
```shell
git tag v1.0.2
git push --tags
```
* enjoy & follow the automated build of the tag

