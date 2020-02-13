# CONTRIBUTING

## Releasing a new version

### Creating a release branch

New versions are released using Git Flow release branches.

When releasing a new version, create a branch using the version number to be released.

```bash
git checkout -b release/1.3.0
```

### Bump the version number

Change the version number in the `pom.xml` file.

### Generating the CHANGELOG.md

We use `gitmoji-changelog` to generate the `CHANGELOG.md` file.

The `CHANGELOG.md` should be generated just after bumping the version number in the `pom.xml` file.

```bash
# install gitmoji-changelog
npm install -g gitmoji-changelog

# generate the changelog
gitmoji-changelog --preset maven
```

### Commit & PR

Commit those changes, and open a pull-request for the release-branch.

The commit message should be like `:bookmark: : release 1.3.0`.

### Github Release and Tag

When the PR is merged, create a Github Release, which points on the previous commit, and add the content of the `CHANGELOG.md` to the release.


