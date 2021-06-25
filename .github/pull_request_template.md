## Description

(Add a description of your pull request & feature here.
pull requests without an adequate description will not be reviewed until one is added.)

## Checklist for the pull request author

- [ ] Tests added for this feature
- [ ] Commit messages follow conventions (see [CONTRIBUTING.md](CONTRIBUTING.md))
- [ ] Commits are combined ( 1 commit / type and scope )
- [ ] Documentation created / updated (if necessary)
- [ ] No new sonarqube issues added
- [ ] CI pipeline is OK
- [ ] Pull request is fast-forward to the `main` branch
- [ ] The last commit of this merge request :
    - [ ] updates the `pom.xml` to a new minor or major version
    - [ ] updates the `CHANGELOG.md`

## Checklist for the project maintainer

- [ ] Review the code, and add a *Review Approval* if everything is ok
- [ ] This pull request will be merged in merge-commit mode
    - (in github or with the CLI, see [CONTRIBUTING.md](CONTRIBUTING.md))

### After the merge

- [ ] Put the tag on the merged commit
    - (in github or with the CLI, see [CONTRIBUTING.md](CONTRIBUTING.md))
- [ ] Follow the pipeline for the tag, which should build & release the package (maven or docker)

## Issues / Links

* Documentation PR : (eg: gaia-app/docs#1)

(Please add issues resolution information when needed)

