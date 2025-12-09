### Release
1. Update version in pom.xml
2. Execute Release action (against master)
3. Wait for new version at https://mvnrepository.com/artifact/gr.spinellis.ckjm/ckjm_ext (can take days)
4. Push tag vX.X and create for it a release at https://github.com/mjureczko/CKJM-extended/releases
5. Rename `target/runable-ckjm_ext-X.X.jar` into ckjm_ext.jar an upload into current release (at https://github.com/mjureczko/CKJM-extended/releases) as asset
6. Update version number in README.md Download section
7. 