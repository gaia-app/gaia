# Changelog

<a name="2.4.0"></a>
## 2.4.0 (2022-08-12)

### Added

- ✨ : add delete module button [[a4766ea](https://github.com/gaia-app/gaia/commit/a4766ea24f88a4130d4d7ae5de024c2eeca04d2b)]
- ✨ : add backend implementation [[b6513f3](https://github.com/gaia-app/gaia/commit/b6513f367f3b78547e9c9112346a79e1999f985d)]
    * ✨ : add backend implementation ([99f6176](https://github.com/gaia-app/gaia/commit/99f6176346e5263b36a5ae7dc9dd611183ef613b))
- ✨ : delete state related to stack [[4f9462e](https://github.com/gaia-app/gaia/commit/4f9462e862bca69a85c01ee7d36775674c0b6f02)]
- ✨ : delete jobs related to stack [[dc9f931](https://github.com/gaia-app/gaia/commit/dc9f931c16414d334459abb864fcff0d374eb3aa)]
- ✨ : add delete stack button [[8358021](https://github.com/gaia-app/gaia/commit/83580211919f316d56a5ef3fab5b6eb33f9b00dd)]
- ➕ : explicit import of spring-boot-starter-logging dependency [[ec6d163](https://github.com/gaia-app/gaia/commit/ec6d163f00918e58d2d59aa25fdd40699215ef58)]
- ✨ : allow running stacks to be updated [[cb178ef](https://github.com/gaia-app/gaia/commit/cb178eff8fa7272e52263283d028541956393c7e)]

### Changed

- 👽 : correct port mapping [[fb48a86](https://github.com/gaia-app/gaia/commit/fb48a86d32b52b4012d218bfa2b5fe9720180556)]
- ♻️ : use correct Base64 type [[6136241](https://github.com/gaia-app/gaia/commit/613624139355c7e8896ccdf2ea4750147614aae7)]
- 👽 : change duration type [[70ee36e](https://github.com/gaia-app/gaia/commit/70ee36e78d1a27981efec72c74e1a4a6f28068bf)]
- ⬆️ : bump cucumber-jvm to 7.5.0 [[93975cc](https://github.com/gaia-app/gaia/commit/93975ccd79353de20b7be98972d821bc67ccd111)]
- ⬆️ : bump antlr4 to 4.10.1 [[34caf21](https://github.com/gaia-app/gaia/commit/34caf2141f9289fe0c1478b4c6eb3bd9dc2340ac)]
- ⬆️ : bump mustache to 0.9.10 [[569523a](https://github.com/gaia-app/gaia/commit/569523a982b04acdfa753b91f33053bc23a512bf)]
- ♻️ : extract mustache.version as property [[5399fe3](https://github.com/gaia-app/gaia/commit/5399fe32c28a6db7c0fb04bb4f472080f382cb98)]
- ⬆️ : bump testcontainers to 1.17.3 [[c3e6aa4](https://github.com/gaia-app/gaia/commit/c3e6aa43fbe22f828762a26a2d97a8bc369608a6)]
- ⬆️ : bump spring-boot-starter-parent to 2.7.2 [[f80a9c2](https://github.com/gaia-app/gaia/commit/f80a9c2666209dd47b6a45480a68ec86c649fe53)]
    * ⬆️ : bump spring-boot-starter-parent to 2.6.10 ([2e4e1fd](https://github.com/gaia-app/gaia/commit/2e4e1fd26f023777b94a9c7f720b4a29377e4e2c))
- 👽 : add JsonProperty to isAdmin field [[0b1a652](https://github.com/gaia-app/gaia/commit/0b1a652cae266e04d3988c6fa810ab49d5064fcc)]
- 📌 : lock jackson-databind to ${jackson-bom.version} [[891632d](https://github.com/gaia-app/gaia/commit/891632dc79f6326171ee6fb1b73280514d7b8bf1)]
- ⬆️ : bump spring-vault to 2.3.2 [[895fa1e](https://github.com/gaia-app/gaia/commit/895fa1e632fd119353ae10830ffed0e015c41df8)]

### Removed

- 🔥 : remove unused jersey.version property [[1e9e5a9](https://github.com/gaia-app/gaia/commit/1e9e5a9101ac01204d09171f4fc5b948504a3c32)]
- 🔥 : remove ClientForwardController [[7933fb5](https://github.com/gaia-app/gaia/commit/7933fb5bf023f9582d8adc901d93ff24c92d8435)]
- 🔥 : remove hasSize assertion [[f82b44f](https://github.com/gaia-app/gaia/commit/f82b44f1734f3d8df01de2d3522acaefc6589986)]

### Miscellaneous

- 🚛 : rename Team to Organization [[e9fb489](https://github.com/gaia-app/gaia/commit/e9fb489288626263ac61416b0cca3f8777ac09d0)]
- 🔀 : merge pull request [#699](https://github.com/gaia-app/gaia/issues/699) from gaia-app/feature/module-refresh-for-manually-imported-modules [[57e49d3](https://github.com/gaia-app/gaia/commit/57e49d361dbe7e180d485aa49763071fc2ea5b3c)]


<a name="2.3.0"></a>
## 2.3.0 (2022-05-08)

### Added

- ✨ : update registry details when module is saved [[e15dbcd](https://github.com/gaia-app/gaia/commit/e15dbcd37af99caf5ec64a08f136d0f14aec1474)]
- ✨ : compute registry details for Gitlab urls [[8436476](https://github.com/gaia-app/gaia/commit/8436476813599b12e8f42c679e8872704e7493ba)]
    * ✨ : compute registry details for Github ssh urls ([c0c3153](https://github.com/gaia-app/gaia/commit/c0c3153940fbcb795d434e45595d2eb9822b7be8))
    * ✨ : compute registry details for Github https urls ([1bfcecc](https://github.com/gaia-app/gaia/commit/1bfceccb1e0fe928e141362c90ef0e6237cf7c2b))

### Changed

- ⬆️ : bump kotlin to 1.6.21 [[d569337](https://github.com/gaia-app/gaia/commit/d5693373fba3a01cfa0a0770333003d491ad1918)]
- ⬆️ : bump jacoco-maven-plugin to 0.8.8 [[4573ad1](https://github.com/gaia-app/gaia/commit/4573ad1e6a69c12801b28c091bce2028dc37c8ea)]
- ⬆️ : bump setup-java action to v3 [[8347df6](https://github.com/gaia-app/gaia/commit/8347df637648b4d18bed9ab6135a6d08b6cb7ec8)]
- ⬆️ : bump kotlin.version to 1.6.0 [[0004c28](https://github.com/gaia-app/gaia/commit/0004c28b0480910b3131b59aa99b386c5057e594)]
- 🔧 : add com.sun.jndi.ldap module export [[808b16d](https://github.com/gaia-app/gaia/commit/808b16d7a795d0bbb6a3375b21071be6ee5667d7)]
- ⬆️ : upgrade to java 17 [[a091b2a](https://github.com/gaia-app/gaia/commit/a091b2a96287b49e6be16cf782884ce7836cc631)]
    *  👷 : migrate to java 17 ([c0d4fed](https://github.com/gaia-app/gaia/commit/c0d4fed44a9d1caa6353d0634f15629394694cbd))
    * ⬆️ : upgrade to java 17 ([619bf1e](https://github.com/gaia-app/gaia/commit/619bf1e92922be21782911a75dec39ea3f17b5f8))
- 🔧 : update default mongodb URL to use 27017 port [[46a3376](https://github.com/gaia-app/gaia/commit/46a33765f1f50bff160b333ac67437dd3066b0db)]
- ♻️ : remove redundant semicolons [[da4c98e](https://github.com/gaia-app/gaia/commit/da4c98e2e90a44d8084117566ed5ad11add8a51e)]
- ♻️ : remove redundant qualifiers [[be3273b](https://github.com/gaia-app/gaia/commit/be3273bf6f40b83e352005b33e314832f5e40139)]
    * 🔥 : remove redundent modifiers ([8649046](https://github.com/gaia-app/gaia/commit/86490460f744eee521a05eacd071bfd9db523169))
- ♻️ : remove unnecessary non-null assertion [[0aeb6eb](https://github.com/gaia-app/gaia/commit/0aeb6eb896944f0aa8007ba0b49b295c7456bd24)]
- ♻️ : replace field injection with constructor [[56de233](https://github.com/gaia-app/gaia/commit/56de2339f208ee4e8b3f44b9dca57646afc8d497)]
- ♻️ : replace null checks with ifPresent() [[446cdd9](https://github.com/gaia-app/gaia/commit/446cdd978d81a560580e8c5cd92babfa766b11e3)]
- ♻️ : replace addAll with parametrized constructor call [[7352877](https://github.com/gaia-app/gaia/commit/73528770688d6b537aa20f59c010f3349d8ad7a7)]
- ♻️ : replace unchecked assignment [[a5ff22b](https://github.com/gaia-app/gaia/commit/a5ff22b53eaf76790141098dc92a9ef0134006d7)]
- 🎨 : remove unnecessary semicolon [[7ae64ea](https://github.com/gaia-app/gaia/commit/7ae64ea7fdfd79a7298038905bf2f9a7cd76e6e5)]
- ♻️ : stop using deprecated constructor [[65e4b36](https://github.com/gaia-app/gaia/commit/65e4b36d4b3068f10cade84d15ecb83dd47f9a05)]
- ♻️ : use redirectUri instead of redirectUriTemplate [[492778b](https://github.com/gaia-app/gaia/commit/492778be3ea9cd383bb9a05d237d64a2e033d3a1)]
- 🚚 : split configuration of oauth providers [[bcee58c](https://github.com/gaia-app/gaia/commit/bcee58c44a81d1eacdcdda2f632d02d9dadc0ddc)]

### Removed

- 🔥 : remove unused imports [[7ffa067](https://github.com/gaia-app/gaia/commit/7ffa067560be69f00877f46efc2281b4523bd538)]
- 🔥 : remove redundant throws clause [[164fdcd](https://github.com/gaia-app/gaia/commit/164fdcde3e49bad4864bac5c85e4cde010b661a7)]
- 🔥 : remove unnecessary imports [[a230bdd](https://github.com/gaia-app/gaia/commit/a230bdd48438d4a72320c241102deeb9c65f423b)]
- 🔥 : remove unused dockerDaemon settings [[929914c](https://github.com/gaia-app/gaia/commit/929914cf8c084d705e8e8c56922283d2dd6bf19e)]

### Fixed

- 🐛 : group parts of the regex together to make the intended operator precedence explicit [[b1aeaca](https://github.com/gaia-app/gaia/commit/b1aeaca6edfbfc86fa90ceda614437e59143c410)]

### Security

- 🔒 : auto create admin user on startup [[f4ba7e9](https://github.com/gaia-app/gaia/commit/f4ba7e9ad4a2ec9ec36fdda206161d681b8653a4)]

### Miscellaneous

-  👷 : set maven to batch-mode [[4d5b722](https://github.com/gaia-app/gaia/commit/4d5b7227a98e7f2173cb49227be08230f0a44dce)]
- 🙈 : add node_modules to .dockerignore [[262ce92](https://github.com/gaia-app/gaia/commit/262ce92962ccd1d1fa2706af3f82a729541437fe)]
- 📝 : add documentation links [[6dcb23a](https://github.com/gaia-app/gaia/commit/6dcb23a998eb2e449002757ffe2fcc942702f012)]


<a name="2.2.0"></a>
## 2.2.0 (2021-08-20)

### Added

- ✨ : add a refresh module definition button [[b039c89](https://github.com/gaia-app/gaia/commit/b039c89e14d66d87e42cd9b22ffef98ec7929751)]
- ✨ : parse output definition when importing a module [[4d477de](https://github.com/gaia-app/gaia/commit/4d477dedf4d78391f51393937e016c8612bacce4)]
- ✨ : extends AzureRM Credentials [[970c3c0](https://github.com/gaia-app/gaia/commit/970c3c0d61cf0fc65a8617ede0e9f71d8e85c24d)]
- ✨ : show outputs at the end of a job [[d126688](https://github.com/gaia-app/gaia/commit/d126688d27aa2dea10865e923c74deaa25ef7c00)]
- ✨ : add scheduled date to jobs [[614fa6d](https://github.com/gaia-app/gaia/commit/614fa6de03738a6c179624d033a2a59a70b95fba)]
- ✨ : do not show apply button if plan is up to date [[02a9aeb](https://github.com/gaia-app/gaia/commit/02a9aeb90a87b7352a12bc0008366f217eed70fc)]
- ✨ : add upToDate property [[adede7a](https://github.com/gaia-app/gaia/commit/adede7af8e668f7a1a66533a9ed0ebd1f0be03c9)]
- ✨ : make destroy jobs upload their plan [[21f17ed](https://github.com/gaia-app/gaia/commit/21f17edd6abfa02ecf8d5082210d0c4d55052e88)]
- ✨ : add terraform blocs support [[d478ebb](https://github.com/gaia-app/gaia/commit/d478ebb777a809d61fe0af0729f829f20d617d9a)]
- ✅ : add types to test data [[43eda58](https://github.com/gaia-app/gaia/commit/43eda58bdc8581a22ce5c120b1043bbdc3f385a7)]
- ✨ : add support for complex variable types [[a9eddec](https://github.com/gaia-app/gaia/commit/a9eddec8b69bf2f509b7cf5e4e3fc24a86d9986a)]
- ✨ : generate tfvar files for complex variable types [[aa9dcf7](https://github.com/gaia-app/gaia/commit/aa9dcf7daf166634e887abb6596f68e2597e86f2)]
- ✅ : add a plan to sample test data [[68227ed](https://github.com/gaia-app/gaia/commit/68227ed55eb043ff22596f5b7054ff007d3c390e)]
- ✨ : upload result plan to server for analysis [[bb87522](https://github.com/gaia-app/gaia/commit/bb875229f7bc880b15ac9027a7983c56ba024e7a)]
- ✨ : save plan for currently running job step [[34058f2](https://github.com/gaia-app/gaia/commit/34058f290d11a9b5428e6ce57fde7cc7cc9650ac)]
- ✨ : add plan deserialization [[5a9e74c](https://github.com/gaia-app/gaia/commit/5a9e74c341c8808674ae2b7336d9ed6622041891)]
- ✅ : fixes the date for the test module [[cc316bd](https://github.com/gaia-app/gaia/commit/cc316bdbc81d894e6701695449489510d3ace26b)]
- ✅ : add navigation to module description page [[0833bec](https://github.com/gaia-app/gaia/commit/0833bec857de0fea1d9b9456c790d197ebeac897)]
- ✅ : add registryDetails to test data [[e3f1f61](https://github.com/gaia-app/gaia/commit/e3f1f615f788ee694009e4d9c18b9c11e5ae32ab)]
- ✨ : add archived stacks filter [[e831e01](https://github.com/gaia-app/gaia/commit/e831e013d7b3459f04447abccfb6a371e44b9109)]
- ✨ : add stack archive support [[c5b4951](https://github.com/gaia-app/gaia/commit/c5b49513b734ca2bd075785b8cb75e2037f61880)]
    * ✨ : add archived state support ([274e037](https://github.com/gaia-app/gaia/commit/274e03755dba66c8637e7b15e9cc1441b4b15950))
- ✅ : stabilize users page tests [[5fcd987](https://github.com/gaia-app/gaia/commit/5fcd98746e902d12e7c9957092a68fb69d601ecc)]
- ✨ : organization creation &amp; deletion [[8c5a778](https://github.com/gaia-app/gaia/commit/8c5a77822bfe53b900d32ed2398e4fb5741b36a4)]
- ✨ : user creation &amp; edition [[d7f1ed4](https://github.com/gaia-app/gaia/commit/d7f1ed427299fea093312f600bb69a24095b88e7)]
- ✅ : add users page navigation test [[d8fe174](https://github.com/gaia-app/gaia/commit/d8fe1745d7d539972719e9599d88dfe47b1b6f31)]
- 🔊 : add error log [[d360e87](https://github.com/gaia-app/gaia/commit/d360e879bd714b1de39c4ec44c24de41ac56ff54)]
- ✅ : add RunnerController tests [[2929365](https://github.com/gaia-app/gaia/commit/2929365ad892b9a4cfde0bd92adfb985aa3d4e7f)]
- ✨ : update workflow with PENDING states [[b5d2867](https://github.com/gaia-app/gaia/commit/b5d2867fa512c625267c4ac3c5811ddac91c88ee)]
- ✨ : extract runner code to gaia-runner [[c37dc1f](https://github.com/gaia-app/gaia/commit/c37dc1f2c1fb10a202a6291726b5145065d2382d)]
- ✅ : convert SeleniumIT to Cucumber tests [[469313f](https://github.com/gaia-app/gaia/commit/469313f21ac62bfe85b5042c6d2b7f09c699143d)]
- ➕ : add cucumber dependency [[46c61df](https://github.com/gaia-app/gaia/commit/46c61df16e445082cd81a954942fc747b9829f59)]
- ✨ : generate tfvar file for the runner [[8bb0b86](https://github.com/gaia-app/gaia/commit/8bb0b8698ae3acb215688683f61a4452c331f700)]
- 🔊 : prefix job logs with [gaia] [[3df9129](https://github.com/gaia-app/gaia/commit/3df9129b8549ca91aab9d1667e7d07f89522b0ea)]
- ✅ : execute mongo scripts with mongo shell instead of db.eval() [[e21c05b](https://github.com/gaia-app/gaia/commit/e21c05be1c8cd7f3e38a35046e4732575e983858)]
- ✨ : encrypt state when encryption service exists [[f9ad238](https://github.com/gaia-app/gaia/commit/f9ad238826246aba56a8ec609ff4c6ccc297dd92)]

### Changed

- ⬆️ : bump jacoco-maven-plugin from 0.8.6 to 0.8.7 [[0ae0587](https://github.com/gaia-app/gaia/commit/0ae05877f11ecd133cb3efa2af8a577a345edd05)]
    * ⬆️ : bump jacoco-maven-plugin from 0.8.5 to 0.8.6 ([bdb586e](https://github.com/gaia-app/gaia/commit/bdb586ea4c484fdeff10fa6c29c62f76fc2f9945))
- ⬆️ : bump kotlin.version to 1.5.21 [[9f6d18f](https://github.com/gaia-app/gaia/commit/9f6d18f54a16d098959c08c887db3d4b6fe9bda0)]
- ⬆️ : update build phase to openjdk-16 [[3c2e8e0](https://github.com/gaia-app/gaia/commit/3c2e8e0dc514b3f8c9054fab788c2a6a37b84762)]
- 💄 : add output description [[407eb1f](https://github.com/gaia-app/gaia/commit/407eb1f3b1bc2d52c6bb0935cc7f8ed4a081c72d)]
- ⬆️ : bump marked to 2.1.2 [[47e2924](https://github.com/gaia-app/gaia/commit/47e29240515e20101165e8a3913fa94d03d6e39b)]
- ⬆️ : bump jquery to 3.6.0 [[fcda27b](https://github.com/gaia-app/gaia/commit/fcda27b127efde6f24575d63b3b41678e865883c)]
- ⬆️ : bump core-js to 3.15.1 [[1402439](https://github.com/gaia-app/gaia/commit/14024393cbec596f85594d92080a011217245137)]
- ⬆️ : update fortawesome dependencies [[e1d6a22](https://github.com/gaia-app/gaia/commit/e1d6a2268ace1ee538cdc2f4c5f55f416644ff51)]
- ⬆️ : update vuejs dependencies [[9063129](https://github.com/gaia-app/gaia/commit/90631294ff26622e0119130bdc868787854c788b)]
- ♻️ : better use of conditions [[11acf19](https://github.com/gaia-app/gaia/commit/11acf197efd33664a2a8d278004d904eb72ea9e5)]
- ♻️ : convert test to Kotlin [[70a5ba0](https://github.com/gaia-app/gaia/commit/70a5ba0014485d1776d8bddb5f8dc21d7876b05f)]
- ⬆️ : bump openjdk from 15-jdk to 16-jdk ([#579](https://github.com/gaia-app/gaia/issues/579)) : [[4945c00](https://github.com/gaia-app/gaia/commit/4945c002c021c4f806bba9a00a6987343f33007e)]
    * ⬆️ : bump openjdk from 14-jdk to 15-jdk ([#434](https://github.com/gaia-app/gaia/issues/434)) ([5fef6a9](https://github.com/gaia-app/gaia/commit/5fef6a9cc28da4816ef1c8ad0d575ad102e95ded))
- ⬆️ : bump testcontainers.version from 1.15.1 to 1.15.2 ([#553](https://github.com/gaia-app/gaia/issues/553)) [[70a9b46](https://github.com/gaia-app/gaia/commit/70a9b461e37e5759a1e9fbb3d5a134177f9f2f8b)]
    * ⬆️ : bump testcontainers.version from 1.14.3 to 1.15.1 ([43c744b](https://github.com/gaia-app/gaia/commit/43c744b0d2fe0db1bca2d16e5bd60de9cd278238))
- ⬆️ :  bump cucumber-jvm.version from 6.9.1 to 6.10.0 ([#554](https://github.com/gaia-app/gaia/issues/554)) [[62e9843](https://github.com/gaia-app/gaia/commit/62e98438b49745048a657e91a590b6b6a87714b3)]
    * ⬆️ : bump cucumber-jvm.version from 6.7.0 to 6.9.1 ([#507](https://github.com/gaia-app/gaia/issues/507)) ([9cd2588](https://github.com/gaia-app/gaia/commit/9cd258881e00ba28b76a4a0498f524a28909caca))
    * ⬆️ : bump cucumber-jvm.version from 6.6.0 to 6.7.0 ([#432](https://github.com/gaia-app/gaia/issues/432)) ([a3d81ca](https://github.com/gaia-app/gaia/commit/a3d81caeae4c2ce163eca276bf0fd701457922e6))
- ⬆️ : bump bootstrap-vue from 2.17.3 to 2.21.2 ([#545](https://github.com/gaia-app/gaia/issues/545)) [[1e16759](https://github.com/gaia-app/gaia/commit/1e16759c4091dcfff3bfa0d15a8aff0b554f69c1)]
    * ⬆️ : bump bootstrap-vue from 2.16.0 to 2.17.3 ([#437](https://github.com/gaia-app/gaia/issues/437)) ([103efbf](https://github.com/gaia-app/gaia/commit/103efbff197554c5585cb164c0d24f56744bbf07))
- ⬆️ 🔒 : bump marked from 1.2.7 to 2.0.0 ([#551](https://github.com/gaia-app/gaia/issues/551)) [[72dc5d7](https://github.com/gaia-app/gaia/commit/72dc5d743a63114e28764eba73215b8008357cf7)]
- ⬆️ : bump kotlin.version from 1.4.10 to 1.4.30 ([#549](https://github.com/gaia-app/gaia/issues/549)) [[6a90999](https://github.com/gaia-app/gaia/commit/6a909990e4fc5dac603cbc984f7a1f3f8df86f22)]
    * ⬆️ : bump kotlin.version from 1.4.0 to 1.4.10 ([#422](https://github.com/gaia-app/gaia/issues/422)) ([91b2f63](https://github.com/gaia-app/gaia/commit/91b2f6304d3ae821eafb7ebebfa8f86845d19c97))
- 💄 : add plan results [[0dab84a](https://github.com/gaia-app/gaia/commit/0dab84a5bc0642ab9e325a2bff50b274cf100d0e)]
- ⬆️ : bump @fortawesome/free-brands-svg-icons from 5.14.0 to 5.15.2 ([#533](https://github.com/gaia-app/gaia/issues/533)) [[5df13ef](https://github.com/gaia-app/gaia/commit/5df13ef3e9abbfed27d5d0752571260b4b3a5670)]
    * ⬆️ : bump @fortawesome/free-solid-svg-icons from 5.14.0 to 5.15.2 ([#534](https://github.com/gaia-app/gaia/issues/534)) ([2513d9e](https://github.com/gaia-app/gaia/commit/2513d9ef733523538a4c1aa56cd13590be57fdc8))
- ⬆️ 🔒 : bump axios from 0.20.0 to 0.21.1 ([#518](https://github.com/gaia-app/gaia/issues/518)) [[fba375b](https://github.com/gaia-app/gaia/commit/fba375bbdd0b979b742d1396d620bf5a46b090dc)]
    * ⬆️ : bump axios from 0.19.2 to 0.20.0 ([#414](https://github.com/gaia-app/gaia/issues/414)) ([176170c](https://github.com/gaia-app/gaia/commit/176170c614ea2feded4ac2705f99ed2015c7fffb))
- ⬆️ : bump spring-boot-starter-parent from 2.4.1 to 2.4.2 ([#535](https://github.com/gaia-app/gaia/issues/535)) [[1441657](https://github.com/gaia-app/gaia/commit/1441657f2d04f2a94f12ac6269f895b445253748)]
    * ⬆️ : bump spring-boot-starter-parent from 2.3.3.RELEASE to 2.4.1 ([#502](https://github.com/gaia-app/gaia/issues/502)) ([7844b6e](https://github.com/gaia-app/gaia/commit/7844b6e718b855b96d536721a4e380cec46d56a4))
- ⬆️ : bump frontend-maven-plugin from 1.10.0 to 1.11.0 ([#503](https://github.com/gaia-app/gaia/issues/503)) [[e5c5bd0](https://github.com/gaia-app/gaia/commit/e5c5bd03eb5e3b678c2f6e13e7d56770f4e63a0b)]
- ⬆️ : bump compiler from 0.9.6 to 0.9.7 ([#460](https://github.com/gaia-app/gaia/issues/460)) [[5006ae9](https://github.com/gaia-app/gaia/commit/5006ae973c388be3b576311a8870726c4002444c)]
- ⬆️ : bump antlr4.version from 4.8-1 to 4.9.1 ([#521](https://github.com/gaia-app/gaia/issues/521)) [[6db9b5b](https://github.com/gaia-app/gaia/commit/6db9b5b7d7e03a89be26ae1cd85241dfc6d42592)]
- ♻️ : self host font [[0c464b4](https://github.com/gaia-app/gaia/commit/0c464b410a4963ed1022c9edcf290af696f291c4)]
- ⬆️ : bump @vue/cli-plugin-babel from 4.5.4 to 4.5.9 ([#487](https://github.com/gaia-app/gaia/issues/487)) [[a376c5c](https://github.com/gaia-app/gaia/commit/a376c5cba5cdb4ce9641961a12447abdc06c4aef)]
    * ⬆️ : bump @vue/cli-plugin-eslint from 4.5.4 to 4.5.7 ([#456](https://github.com/gaia-app/gaia/issues/456)) ([74d0450](https://github.com/gaia-app/gaia/commit/74d0450ab28fc8447f2210546190bbf6d9ad8eaa))
- ⬆️ : bump eslint from 7.8.1 to 7.16.0 ([#505](https://github.com/gaia-app/gaia/issues/505)) [[a324b96](https://github.com/gaia-app/gaia/commit/a324b967db17a1db9dae915086d4b56faf90e1df)]
    * ⬆️ : bump eslint from 7.7.0 to 7.8.1 ([#420](https://github.com/gaia-app/gaia/issues/420)) ([fc90a19](https://github.com/gaia-app/gaia/commit/fc90a191e5e163467a616a2a70407adf92bbfdfb))
- ⬆️ : bump eslint-plugin-import from 2.22.0 to 2.22.1 ([#444](https://github.com/gaia-app/gaia/issues/444)) [[7420cb1](https://github.com/gaia-app/gaia/commit/7420cb19993e96539d0915baf93f8fa5b9cec84e)]
- ⬆️ : bump pitest-maven from 1.5.2 to 1.6.1 [[53d096f](https://github.com/gaia-app/gaia/commit/53d096f475ff39278734ea626703ba6f405f941d)]
- 💄 : add user edition views [[6910005](https://github.com/gaia-app/gaia/commit/69100053e080fb7430292a163b62313ca0fde35c)]
- ♻️ : use stepId instead of step object [[27f35db](https://github.com/gaia-app/gaia/commit/27f35db7d59dac3e4d921adbf3f2ee807fd5147e)]
- 💄 : add PENDING state management [[a8ea854](https://github.com/gaia-app/gaia/commit/a8ea854909dd313aa9a6e7688d5b4d2cfb4975cf)]
- 🔧 : use dependency injection for webdriver [[2a03f72](https://github.com/gaia-app/gaia/commit/2a03f725124401e82a850af98d80cb13c2aa49cd)]
- ♻️ : create runner api [[6a6bb77](https://github.com/gaia-app/gaia/commit/6a6bb771e61ca80eda95599b194e57092ec421ae)]
- ⬆️ : bump @fortawesome/vue-fontawesome from 0.1.10 to 2.0.0 ([#421](https://github.com/gaia-app/gaia/issues/421)) [[daa34ab](https://github.com/gaia-app/gaia/commit/daa34ab2fa1e4d3c326a6fada628add8d36f5ce2)]
- ⬆️ : use mongodb 4.4 [[dbd1469](https://github.com/gaia-app/gaia/commit/dbd1469ded7e35e769928bbde76012fdbd490ba9)]
- ⬆️ : bump copy-webpack-plugin from 6.0.3 to 6.1.0 ([#417](https://github.com/gaia-app/gaia/issues/417)) [[0ca21ea](https://github.com/gaia-app/gaia/commit/0ca21ea37efa7c9cb6c0e4ee1330f4f1366905d3)]
- ⬆️ : bump vue and vue-template-compiler ([#413](https://github.com/gaia-app/gaia/issues/413)) [[aebfac8](https://github.com/gaia-app/gaia/commit/aebfac8f1dc127b35f50b02d058bc0697e9c3422)]
- ♻️ : better use of dependency injection [[3bfcfd4](https://github.com/gaia-app/gaia/commit/3bfcfd451573663394847a1eba57787f09b5e475)]

### Removed

- ➖ : remove bootstrap direct dependency [[833aef7](https://github.com/gaia-app/gaia/commit/833aef7256510ac0fddfdd81b0a167a79f43672f)]
- 🔥 : remove pitest dependency [[ad8b39c](https://github.com/gaia-app/gaia/commit/ad8b39c91dc2467956284696be7ce7665fc7bc23)]
- 🔥 : remove unused css [[9424fbf](https://github.com/gaia-app/gaia/commit/9424fbf6b5e7ea963826133c4fa4570db9c7bdbd)]
- 🔥 : remove .travis.yml [[196901f](https://github.com/gaia-app/gaia/commit/196901f379d0f567d4c340d432b54b92f15a5e9d)]
- ➖ : remove guava from dependency management [[0137d6e](https://github.com/gaia-app/gaia/commit/0137d6e237cece1a08570ab0f169e52b051d8470)]
- ➖ : remove docker-java dependency [[8ffbeaa](https://github.com/gaia-app/gaia/commit/8ffbeaa438aeb83da524c88d32d714266c33219e)]

### Fixed

- 🐛 : correct Settings env vars propagation to runner [[3c47ce7](https://github.com/gaia-app/gaia/commit/3c47ce7d41d352552fef068c5335fed8c5efed40)]
- 🐛 : set the stack status to TO_UPDATE on variable change [[2292191](https://github.com/gaia-app/gaia/commit/2292191b753421522a5a28ec6b26eced8ec12610)]
- 🐛 : fix deleteCount variable name [[cc447e1](https://github.com/gaia-app/gaia/commit/cc447e13d9686c6c12a1c3293408618b5142cf1a)]
- 🐛 : prevent spring from loading LdapAutoConfiguration [[8f7c767](https://github.com/gaia-app/gaia/commit/8f7c7672e29f9316e420563934c15e9d981e4f6b)]
- 🐛 : ignore dummy providers when info is parsed from resources [[346497f](https://github.com/gaia-app/gaia/commit/346497f1de86b140a8cd10debb68247ca76b469e)]
- 🐛 : remove ref&#x3D;master parameter from Github requests [[6fc5a6f](https://github.com/gaia-app/gaia/commit/6fc5a6f73aa87e5f19415adcaa23da898539507e)]
- 🐛 : fixes manual import navigation after module creation [[75c07e8](https://github.com/gaia-app/gaia/commit/75c07e84ae5a8fd4012359a114fd17a4432a321e)]
- 🐛 : add export for java.naming/com.sun.jndi.ldap package [[724fccf](https://github.com/gaia-app/gaia/commit/724fccf3e0a5dc42e47bd3846757a1200046352b)]
- 🐛 : use ignoring minutes instead of seconds [[29ae28f](https://github.com/gaia-app/gaia/commit/29ae28fc80cd9dd7ed2ebbc17b865e904b34337c)]
- 🐛 : correct NPE when stack has no credentials [[e1c28a9](https://github.com/gaia-app/gaia/commit/e1c28a9891cee617d16799095d582a6e71f30845)]

### Security

- 🔒 : authenticate users with database password [[2b6d435](https://github.com/gaia-app/gaia/commit/2b6d435ace2244e62c49804e141972301607d695)]
- 🔒 : add updatable isAdmin flag [[685db79](https://github.com/gaia-app/gaia/commit/685db793ac3cc1c7caf407c283e55811ba793d62)]

### Miscellaneous

- 📝 : add contribution guidelines [[e9c31bc](https://github.com/gaia-app/gaia/commit/e9c31bc0c332e3e387321b951bfb3173fc0975e8)]
-  👷 : rename master branch to main [[b106a97](https://github.com/gaia-app/gaia/commit/b106a9795e8c5c574475e3e9ececadc2199c894a)]
-  👷 : add percy on baseline branch [[cd9fea8](https://github.com/gaia-app/gaia/commit/cd9fea833e1a0c13f1e63f3431d9c4579e11ff24)]
-  👷 : add percy tests [[aca01a9](https://github.com/gaia-app/gaia/commit/aca01a9a129173739db06fd527a4df739eeef812)]
-  👷 : add github workflow for tests [[55a3320](https://github.com/gaia-app/gaia/commit/55a332056ff81ed3fe3f11b5aeff44fb8f6a346d)]
-  👷 : remove docker build [[9ed36bb](https://github.com/gaia-app/gaia/commit/9ed36bbe26dba18a39dd529485e74a54715c6b74)]
- 🐋 : remove docker socket from volumes mount [[f72271f](https://github.com/gaia-app/gaia/commit/f72271f641eb2b8732bee25f1c4ee4d1b39752bc)]
- 📝 : add runner api properties [[3a48a2b](https://github.com/gaia-app/gaia/commit/3a48a2b03c8b4665cc3158618fd577c5e9b1759a)]
- ⚗️ : use junit-platform-engine [[f1d5437](https://github.com/gaia-app/gaia/commit/f1d5437e5e1e8200a32c6b448135df031d4ac415)]
-  👷 : add separate stage for e2e [[d177a33](https://github.com/gaia-app/gaia/commit/d177a33b1db9b41d14a74fa8fb7cf0baf5ae1f80)]


<a name="2.1.0"></a>
## 2.1.0 (2020-08-19)

### Added

- ✨ : add GOOGLE_PROJECT to google credentials [[cc16b32](https://github.com/gaia-app/gaia/commit/cc16b324d6c33e342520554343a6cbbfab23d78a)]
- ✅ : stabilize percy tests [[66517a2](https://github.com/gaia-app/gaia/commit/66517a2df409bc8c7fe6d79615668d8998edbd57)]
- ✨ : add docker-compose vault example [[609e551](https://github.com/gaia-app/gaia/commit/609e551b07e92d93c864f0cee80b6335d287c476)]
- ✨ : get provider list from the api [[e7d6c56](https://github.com/gaia-app/gaia/commit/e7d6c5618a25c3a2ce3a6689ffb4e98a1343f535)]
- ✨ : add vault-aws credentials [[9bfbc79](https://github.com/gaia-app/gaia/commit/9bfbc79f7f9873295dd0166248c51891b40e4361)]
- ✨ : add NoOpEncryptionService [[a6821f4](https://github.com/gaia-app/gaia/commit/a6821f401c667f9af036d08a460630a9db58fd18)]
- ✅ : add creation test for credential sub classes [[6b4494c](https://github.com/gaia-app/gaia/commit/6b4494c2c308274d7c5d7650f51fb0c7a1c10a0a)]
- ➕ : add spring-vault-core dependency [[3073f43](https://github.com/gaia-app/gaia/commit/3073f43eeb93ddec4561bee1ebee1e502ed186b2)]
- ✨ : add credentials selection to stack [[60ef68d](https://github.com/gaia-app/gaia/commit/60ef68d8cc34a5182bf07eff6b57505aaefc7821)]
- ✨ : add main provider selection in modules [[80d3d23](https://github.com/gaia-app/gaia/commit/80d3d230a2418da4f46c8e33b2aade77bd2ff6bf)]
- ✨ : delete credentials [[ddcb7ce](https://github.com/gaia-app/gaia/commit/ddcb7ce493022a26bbfec646e2ffefed80d50d42)]
- ✨ : add create credentials page [[5b2ef99](https://github.com/gaia-app/gaia/commit/5b2ef99b7c2a0dc3a6dd63249fa74c9a8f26ca00)]
- ✨ : add credentials details page [[7e7fcdb](https://github.com/gaia-app/gaia/commit/7e7fcdb05c2c9e51b652f392af7ce3a2ebb3426e)]
- ✨ : add credentials list page [[3f234aa](https://github.com/gaia-app/gaia/commit/3f234aa8b661f778fca5fbda7fa2773c3acaeab1)]
- ✨ : add CredentialsRestController [[a1e23ab](https://github.com/gaia-app/gaia/commit/a1e23abf3c9a8a56f103f4f2f9b1f392222f1bbd)]
- ✨ : add credentials mgmt to jobs [[ae437ae](https://github.com/gaia-app/gaia/commit/ae437aeae998b9edaf5e8a8b93b21e4bbc186b05)]
- ✅ : assert that TF_IN_AUTOMATION env var is used [[fdff3a5](https://github.com/gaia-app/gaia/commit/fdff3a55148b1f09b1454ef39bb4c00d2125daf9)]
- 👷‍♂️ : skip default install [[814df1f](https://github.com/gaia-app/gaia/commit/814df1fd46d65d401a455d0470bc5bcd33edc5c9)]
- ➕ : replace deprecated docker-client by docker-java [[15f17ab](https://github.com/gaia-app/gaia/commit/15f17ab45c8f2d1b0738e036374b762c0e69a4d1)]
- ➕ : add spring-boot-starter-validation [[36f2d70](https://github.com/gaia-app/gaia/commit/36f2d70f051be0c22e0efb2e56d5ad0dcb763093)]
- 👷‍♂️ : add node_modules to cached directories [[a8ff93a](https://github.com/gaia-app/gaia/commit/a8ff93a09cb9ebb3ed5e13fc9b0a8b88eb1bbb70)]
- 👷‍♂️ : use node 12 for percy [[85854d9](https://github.com/gaia-app/gaia/commit/85854d90ada841e86bced0b48623e1967fdd6add)]
- ✅ : add the build-commit class for percy to ignore this component [[0e19a94](https://github.com/gaia-app/gaia/commit/0e19a94d5484244dd07c158ddb74a8fb21b57716)]
- 👷‍♂️ : add support for no-percy labels on PRs [[ad1cf1b](https://github.com/gaia-app/gaia/commit/ad1cf1b06c81e77bdd15634a57d5f6713cd66ee6)]
- 👷‍♂️ : reactivate percy tests [[afa7160](https://github.com/gaia-app/gaia/commit/afa71602a419d69071b37340dd85b7f8f570a368)]

### Changed

- ⬆️ : bump eslint to 7.6.0 [[8f4be17](https://github.com/gaia-app/gaia/commit/8f4be1755aa4a920e963fd73fa0e05bdfd720c45)]
- ⬆️ : bump @vue/cli-plugin-vuex to 4.5.4 [[aa407e7](https://github.com/gaia-app/gaia/commit/aa407e774efd8f016226fa1b2b19ce4eab22057e)]
- ⬆️ 🔒 : bump elliptic to 6.5.3 ([#372](https://github.com/gaia-app/gaia/issues/372)) [[30a62f9](https://github.com/gaia-app/gaia/commit/30a62f9320821f4a870e880e536a18d63bfdb9a2)]
- ⬆️ : bump @vue/cli-plugin-router to 4.5.4 ([#405](https://github.com/gaia-app/gaia/issues/405)) [[2f8b2c6](https://github.com/gaia-app/gaia/commit/2f8b2c6684f3ae9008cbbc3cc28420c39ec32163)]
- ⬆️ : bump @vue/cli-service to 4.5.4 ([#404](https://github.com/gaia-app/gaia/issues/404)) [[720c637](https://github.com/gaia-app/gaia/commit/720c637a897c0b5744c2912b5c6b05b2b06b6305)]
- ⬆️ : bump @vue/cli-plugin-babel to 4.5.4 ([#403](https://github.com/gaia-app/gaia/issues/403)) [[1de3dc7](https://github.com/gaia-app/gaia/commit/1de3dc7cc9e08975ef266a00953a18e3002ca84e)]
- ⬆️ : bump @vue/cli-plugin-eslint to 4.5.4 ([#402](https://github.com/gaia-app/gaia/issues/402)) [[2ac15f6](https://github.com/gaia-app/gaia/commit/2ac15f693560be45503bc920e3b4d2aca91e06be)]
- ⬆️ : bump percy-java-selenium to 0.1.4 ([#397](https://github.com/gaia-app/gaia/issues/397)) [[d0cbb97](https://github.com/gaia-app/gaia/commit/d0cbb97fdf97744597deed7a1354a4aff73ff23e)]
- ⬆️ : bump vue-router to 3.4.3 ([#396](https://github.com/gaia-app/gaia/issues/396)) [[117b871](https://github.com/gaia-app/gaia/commit/117b8716d2eaf8406507f7d20d8f9511f130aa9d)]
- ⬆️ : bump bootstrap to 4.5.2 ([#382](https://github.com/gaia-app/gaia/issues/382)) [[631d21c](https://github.com/gaia-app/gaia/commit/631d21c03b82efa10386c4a92b36ad4f611027e9)]
- ⬆️ : bump kotlinx-coroutines-core to 1.3.9 ([#399](https://github.com/gaia-app/gaia/issues/399)) [[a2e48ff](https://github.com/gaia-app/gaia/commit/a2e48ff4502b5d4762970fa6123af9959ea4130d)]
- 💄 : update stack list cards [[eefc515](https://github.com/gaia-app/gaia/commit/eefc51556dfca0508f2f07c31e1385d35c1ed2ef)]
- ♻️ : update Stack to have a DbRef onto Module [[7b879c4](https://github.com/gaia-app/gaia/commit/7b879c4ca92301e4e62c307a0d9109b188d32e63)]
- 💄 : add 1rem padding to markdown-body [[36fa29b](https://github.com/gaia-app/gaia/commit/36fa29b53c862225dbefc7ce3ea9d887b88a8ef7)]
- 💄 : update layout of modules list [[9c07ac7](https://github.com/gaia-app/gaia/commit/9c07ac76558fab5f8b81253d27a660e25ae79466)]
- 🔧 : limit github scope to public repositories [[57c03d8](https://github.com/gaia-app/gaia/commit/57c03d894c7d1067d5a228c55e445b4de7ea3eaf)]
- ⬆️ : bump bootstrap-vue to 2.16.0 ([#371](https://github.com/gaia-app/gaia/issues/371)) [[82df2c5](https://github.com/gaia-app/gaia/commit/82df2c551251a7a85987713926228cf51080bda8)]
- ⬆️ : bump spring-boot-starter-parent ([#398](https://github.com/gaia-app/gaia/issues/398)) [[9ff029b](https://github.com/gaia-app/gaia/commit/9ff029beb96caea4801684e7c62e4908690d0338)]
- ⬆️ : bump kotlin.version to 1.4.0 ([#400](https://github.com/gaia-app/gaia/issues/400)) [[d7e3292](https://github.com/gaia-app/gaia/commit/d7e32927b53fe0c5663168cd567f021b473e4155)]
- 💄 : use flex-box to display credentials [[d528291](https://github.com/gaia-app/gaia/commit/d528291c2e4aec89c1798ba3e9a46a8c922c0c91)]
- ♻️ : add CredentialsService [[ec6f1c0](https://github.com/gaia-app/gaia/commit/ec6f1c0fa005e9e2afc45891a4ee0d27839c71e1)]
- 🚸 : show a message when no readme is found [[0e823f5](https://github.com/gaia-app/gaia/commit/0e823f5aedd2faf028446da420851e947fcf7c04)]
- ⬆️ : bump @fortawesome/fontawesome-svg-core to 1.2.30 ([#364](https://github.com/gaia-app/gaia/issues/364)) [[dd61542](https://github.com/gaia-app/gaia/commit/dd61542edbd17ffa208ececbe02084f1f4c64f3f)]
- ⬆️ : bump @fortawesome/free-solid-svg-icons to 5.14.0 ([#363](https://github.com/gaia-app/gaia/issues/363)) [[a250700](https://github.com/gaia-app/gaia/commit/a25070051c6a09f9cc8f41d02ab1c66fbea78d14)]
- ⬆️ : bump @fortawesome/free-regular-svg-icons ([#362](https://github.com/gaia-app/gaia/issues/362)) [[d5cb9d5](https://github.com/gaia-app/gaia/commit/d5cb9d528774d071b9bb5110e2d5c4e966ecc44d)]
- ⬆️ : bump copy-webpack-plugin to 6.0.3 ([#354](https://github.com/gaia-app/gaia/issues/354)) [[d34c92f](https://github.com/gaia-app/gaia/commit/d34c92f9ba08e4c1a5d2a21bf5a38465a3e73809)]
- ⬆️ : bump vuex to 3.5.1 ([#355](https://github.com/gaia-app/gaia/issues/355)) [[03e9c5e](https://github.com/gaia-app/gaia/commit/03e9c5e126101821218dd0927c049342a7f9b8fb)]
- ⬆️ : bump @fortawesome/free-brands-svg-icons from 5.13.1 to 5.14.0 ([#360](https://github.com/gaia-app/gaia/issues/360)) [[a802b20](https://github.com/gaia-app/gaia/commit/a802b204d0d4eb93de59209321614233837596b1)]
- ⬆️ 🔒 : bump lodash to 4.17.19 ([#359](https://github.com/gaia-app/gaia/issues/359)) [[8336487](https://github.com/gaia-app/gaia/commit/8336487aef5a8c1f148d285ed6b70c7ef1f1ea97)]
- 🚨 : correct linter warning [[87e46ff](https://github.com/gaia-app/gaia/commit/87e46ffb8b224ca2fcc308858e4a6d64a5dded48)]
- ⬆️ : bump eslint-plugin-import to 2.22.0 ([#357](https://github.com/gaia-app/gaia/issues/357)) [[72fc1ee](https://github.com/gaia-app/gaia/commit/72fc1ee1acfcc1ed95e223263489c994a8893103)]
- ⬆️ : bump @vue/eslint-config-airbnb to 5.1.0 ([#349](https://github.com/gaia-app/gaia/issues/349)) [[71fd567](https://github.com/gaia-app/gaia/commit/71fd5679fdb976256fe759f9d1b9d96bcda3323b)]
- 💄 : new module page list [[cee22ea](https://github.com/gaia-app/gaia/commit/cee22eadbfc579b4da73c31ea0a528bfdaad5a19)]
- 🔧 : add ci-tu profile [[d94d368](https://github.com/gaia-app/gaia/commit/d94d36884a29f925ff372f56d6a63141c689de1a)]
- ⚡ : use singleton container pattern [[f109f5e](https://github.com/gaia-app/gaia/commit/f109f5e9d771d68b848e7acb0ca1d8c327be6b5e)]
- ⬆️ : bump docker-java.version to 3.2.5 ([#350](https://github.com/gaia-app/gaia/issues/350)) [[592fe12](https://github.com/gaia-app/gaia/commit/592fe1295a3fa58ea71ec0ccb017cb40c2192fa5)]
- ⬆️ : bump @fortawesome/vue-fontawesome to 0.1.10 ([#330](https://github.com/gaia-app/gaia/issues/330)) [[6d8452b](https://github.com/gaia-app/gaia/commit/6d8452bb829e62e2de99bab136b79b6ceaf4e317)]
- ⬆️ : bump pitest-maven to 1.5.2 ([#290](https://github.com/gaia-app/gaia/issues/290)) [[b0c6941](https://github.com/gaia-app/gaia/commit/b0c6941e660d27987f415a5ce43415f61864e25c)]
- ⬆️ : bump guava to 29.0-jre ([#255](https://github.com/gaia-app/gaia/issues/255)) [[53bde1d](https://github.com/gaia-app/gaia/commit/53bde1d9ec86278ca3173c5150829777ce4d83c6)]
- ♻️ : migration to docker-java [[d4229d1](https://github.com/gaia-app/gaia/commit/d4229d1a11b9f8613ae656a69095205837d4795e)]
- ⬆️ 🔒 : bump websocket-extensions to 0.1.4 ([#314](https://github.com/gaia-app/gaia/issues/314)) [[79cb1f4](https://github.com/gaia-app/gaia/commit/79cb1f4e845623feb27f6ba9bfa415f84c3b7b60)]
- ⬆️ : bump testcontainers.version to 1.14.3 ([#310](https://github.com/gaia-app/gaia/issues/310)) [[6d391c0](https://github.com/gaia-app/gaia/commit/6d391c06db56742703a5cd72dfd5360ec21176eb)]
- 🚚 : rename groupId [[3fdb79e](https://github.com/gaia-app/gaia/commit/3fdb79ed1eaecaa8645ef87c4822b4aa01f77fa3)]
- 🚚 : rename root package [[008a87e](https://github.com/gaia-app/gaia/commit/008a87e466e37276a67c165bb97c440320b3e902)]
- 📌 : re-create package-lock.json [[44dcbd6](https://github.com/gaia-app/gaia/commit/44dcbd61c2254368e8fe014c0f4a8dab8c8aa233)]
- ⬆️ : bump marked to 1.1.0 ([#297](https://github.com/gaia-app/gaia/issues/297)) [[0f0ba86](https://github.com/gaia-app/gaia/commit/0f0ba86367ec7a04bc29dd1aed2682b2164171c7)]
- ⬆️ : bump jquery to 3.5.0 ([#283](https://github.com/gaia-app/gaia/issues/283)) [[6042616](https://github.com/gaia-app/gaia/commit/6042616949c498db3c7a83b7d731bfc5d2a894db)]
- ⬆️ : bump babel-eslint to 10.1.0 ([#272](https://github.com/gaia-app/gaia/issues/272)) [[afef1fd](https://github.com/gaia-app/gaia/commit/afef1fd10a296864e88c0659ee3bbd52f21b9061)]
- ⬆️ : bump frontend-maven-plugin to 1.10.0 ([#288](https://github.com/gaia-app/gaia/issues/288)) [[682152a](https://github.com/gaia-app/gaia/commit/682152a2d96a745b226b759a76391410b8ba6176)]
- ⬆️ : bump corejs-typeahead to 1.3.1 ([#259](https://github.com/gaia-app/gaia/issues/259)) [[a4d2f41](https://github.com/gaia-app/gaia/commit/a4d2f41fc0afdcb5fd8719e8c546b67d9a5fc91d)]
- ⬆️ : bump core-js to 3.6.5 ([#268](https://github.com/gaia-app/gaia/issues/268)) [[a9fdbf6](https://github.com/gaia-app/gaia/commit/a9fdbf65eff8185e4bfbe8cdb7565ea76b35a437)]
- 🔧 : configure percy to take snapshots at 1920 width [[d205ba5](https://github.com/gaia-app/gaia/commit/d205ba5eb13b2bf17acaecbbb1085d07dd631f27)]
- ⬆️ : bump openjdk to 14-jdk ([#240](https://github.com/gaia-app/gaia/issues/240)) [[4ee26a9](https://github.com/gaia-app/gaia/commit/4ee26a9adb6d8050e1039fbb0126483785ec2922)]
- ⬆️ : bump maven to 3-jdk-14 ([#157](https://github.com/gaia-app/gaia/issues/157)) [[a35402d](https://github.com/gaia-app/gaia/commit/a35402d145f6bfda2c087b51f17d1a0450cfff18)]
- ⬆️ 🔒 : Bump acorn to 6.4.1 ([#258](https://github.com/gaia-app/gaia/issues/258)) [[edbac13](https://github.com/gaia-app/gaia/commit/edbac13b400c2f3d4da5ec07863658906a1811f3)]

### Removed

- 🔥 : remove unused batch operations [[9f000ee](https://github.com/gaia-app/gaia/commit/9f000eebbba119715f02f5947ace04383235d56a)]
- 🔥 : remove package-lock.json [[ed45e52](https://github.com/gaia-app/gaia/commit/ed45e52ccf24b054198156693bf9358590dbda04)]

### Fixed

- 🐛 : fetch the maximum page size for github repositories [[773facb](https://github.com/gaia-app/gaia/commit/773facb6d206be91310fb4e858272cb9b5365a23)]
- 🐛 : delay job refresh when starting [[b866aaa](https://github.com/gaia-app/gaia/commit/b866aaa66adbe74989c83d3234082554e032ff69)]
- 🐛 : fix README loading [[049320c](https://github.com/gaia-app/gaia/commit/049320cf64dbd951fe59a60b2c0d347a2dc51525)]
- 🐛 : authentication can be null for anonymous requests [[6471721](https://github.com/gaia-app/gaia/commit/6471721c0f053632275b8cdb157f7579e8a80583)]
- 🐛 : correct a vue error when provider is null [[2aa1b0d](https://github.com/gaia-app/gaia/commit/2aa1b0d628e5e1954f911c4da39bebaab294c632)]
- 🐛 : add exit 0 at the end of scripts [[a790649](https://github.com/gaia-app/gaia/commit/a79064900a37275fc1f7866c865af958a0638275)]
- 💚 : correct MongoClient usage [[3d6e4a2](https://github.com/gaia-app/gaia/commit/3d6e4a2f73c0db8c9828a2e4a388fb4105ad2d56)]
- 💚 : remove jackson.version usage [[2cb7090](https://github.com/gaia-app/gaia/commit/2cb70902e15d452bef602902d38cdf0432c773fe)]

### Security

- 🔒 : encrypt Azure &amp; Google credentials [[920fa83](https://github.com/gaia-app/gaia/commit/920fa8337f55a185c59f33c8bc4a1f976cda54a9)]
- 🔒 : add encryption to AWS credentials [[921a82c](https://github.com/gaia-app/gaia/commit/921a82c27591bb731bb236fa3a076ff273adf036)]
- 🔒 : add EncryptionService [[e3d2273](https://github.com/gaia-app/gaia/commit/e3d2273f47d68ff39e8e07c0212a7533054fc0cf)]
- 🔒 : avoid credentials leakage through Jobs API [[9059bc5](https://github.com/gaia-app/gaia/commit/9059bc5c9124385bd70463d57c18e63a15a84fec)]
- 🔒 : use credentials id instead of credentials object to avoid leakage [[c85f1e8](https://github.com/gaia-app/gaia/commit/c85f1e803428537a2d06d6df3d8fbece10d79645)]
- 🔒 : scope credentials to users [[c787392](https://github.com/gaia-app/gaia/commit/c78739218a88238658cb1d85ea3711533030eccf)]

### Miscellaneous

- 📝 : correct docker pulls badge [[461cf63](https://github.com/gaia-app/gaia/commit/461cf634b25d985dd52501392dc45e808f3a40de)]
- 📝 : use absolute paths for images [[fc5d9a6](https://github.com/gaia-app/gaia/commit/fc5d9a691f42e848e486a63f6c898c53097af5c3)]

<a name="2.0.0"></a>
## 2.0.0 (2020-04-10)

### Added

- ✅ : make selenium tests work again [[0cc5419](https://github.com/gaia-app/gaia/commit/0cc5419cf342aede4ddd23e0d25ed0b1ba2731ad)]
- ✨ : add api to plan or apply a job [[6b6664d](https://github.com/gaia-app/gaia/commit/6b6664df2270b9db22e5fa7439a5550590c676e2)]
- ✨ : add api to run or destroy a stack [[b6f2210](https://github.com/gaia-app/gaia/commit/b6f2210a6ba7604eb474811a9249919819bcffdb)]
- ✨ : add service to display confirm modal [[f80cff9](https://github.com/gaia-app/gaia/commit/f80cff9236935b66765409eefaff23a0cacdf667)]
- ✨ : add api to get job [[f2bc5ee](https://github.com/gaia-app/gaia/commit/f2bc5ee85a16428be4e1af29d6bd19ac45ab9337)]
- ➕ : add ansi-to-html dependency [[22a81bb](https://github.com/gaia-app/gaia/commit/22a81bb9e143c52c6da25d48c95eb76db3e5712f)]
- ✨ : add api to get stacks [[6310b45](https://github.com/gaia-app/gaia/commit/6310b45b33f27fe76ca43d0bcc191e72dffa7086)]
- ✨ : add modal service to display notifications [[9f7cba6](https://github.com/gaia-app/gaia/commit/9f7cba6c6ccb76d3fc71946563045e390a78af62)]
- ✨ : add axios interceptors to handle responses [[2ff794f](https://github.com/gaia-app/gaia/commit/2ff794f674b1de6cf249ef9bca1ca9d48895f57b)]
- ✨ : add api service for users [[0194fb8](https://github.com/gaia-app/gaia/commit/0194fb862a0d44dcd0798f9ed57042c003e045df)]
- ✨ : add api service for settings [[67b6ef1](https://github.com/gaia-app/gaia/commit/67b6ef122172ef704d1de3e51e63b50233a1b846)]
- ✨ : add resource to expose settings [[1a6213f](https://github.com/gaia-app/gaia/commit/1a6213f7cf060fdf6989e3c614bcbbea48f91d44)]
- ✨ : add api service for dashboard summary [[f2d5e68](https://github.com/gaia-app/gaia/commit/f2d5e686444dbb15ec17647269e334f70edad7d1)]
- ✨ : add resource to expose dashboard summary [[557674f](https://github.com/gaia-app/gaia/commit/557674f2ffae16b2f8b99007ee1afab9ccb4f191)]
- ✨ : update page title for each route [[1d13bcc](https://github.com/gaia-app/gaia/commit/1d13bcca29f4fa33371b02c3a5c264c9bedfc297)]
- ➕ : add uuid dependency [[941b11f](https://github.com/gaia-app/gaia/commit/941b11fdfa2564056c75cb46bf614c2d045eb6d4)]
- ✅ : disable selenium IT until migration completed [[cd7a38c](https://github.com/gaia-app/gaia/commit/cd7a38c05a53d52b8d5eab231ffbbeef79dcdcf7)]
- ✅ : fix tests after security process rework [[81e78a3](https://github.com/gaia-app/gaia/commit/81e78a354936f51ee33e9072d8554d663f4f4bd3)]
- ✨ : add component for oauth signin [[dded8ed](https://github.com/gaia-app/gaia/commit/dded8edff855faf3e92605d29b4d3acd9793e784)]
- ✨ : add default layout [[129ef69](https://github.com/gaia-app/gaia/commit/129ef6970287b9e06317c47fb06884b5b6f429e9)]
- ✨ : add service to manage cookie [[2317c3b](https://github.com/gaia-app/gaia/commit/2317c3ba258ea6b3722fbeb2dd3bd20b3dc2a46d)]
- ✨ : add api service for build infos [[82b8155](https://github.com/gaia-app/gaia/commit/82b81557c573643cef35c28b7ad70091d75eab14)]
- ✨ : add error layout [[93ffff5](https://github.com/gaia-app/gaia/commit/93ffff51e6744eb15f2c02631375f9a1140edf07)]
- ✨ : add layout system and none layout [[c6f151b](https://github.com/gaia-app/gaia/commit/c6f151bfc8bb4eee69fb2d48077452d45da6a8b2)]
- ✨ : add navigation guard [[ca10adb](https://github.com/gaia-app/gaia/commit/ca10adbad83064758f4b5ea6a10d7ff3a57349a2)]
- ✨ : add service for session storage [[0a9ab01](https://github.com/gaia-app/gaia/commit/0a9ab01d29a705ee3864b506ba2222cb84747a61)]
- ✨ : add store management for user session [[82a1cf6](https://github.com/gaia-app/gaia/commit/82a1cf624ba69be0933b9beed2f88d3975fee2a2)]
- ✨ : add authentication api service in client [[cc1d3d7](https://github.com/gaia-app/gaia/commit/cc1d3d73e527a7e9f1b43a34970ffb5aabd7d7b2)]
- ➕ : add axios dependency [[3e3bf0c](https://github.com/gaia-app/gaia/commit/3e3bf0cd64e39640aecc6058269704ad9e692e67)]
- ➕ : add node modules which were previously webjars [[50e55b8](https://github.com/gaia-app/gaia/commit/50e55b8b411d1763177d18d96416793547888774)]

### Changed

- ♻️ : change the route for stack creation [[12fdcff](https://github.com/gaia-app/gaia/commit/12fdcff1ce3163cee7d91acb03d31ec8e54a16e9)]
- 💄 : add overlay on dashboard loading [[27a62aa](https://github.com/gaia-app/gaia/commit/27a62aad929660fb62229dc524420cba4c83aef8)]
- ⬆️ : bump bootstrap-vue from 2.4.2 to 2.10.1 [[f00edb8](https://github.com/gaia-app/gaia/commit/f00edb87aec18a3a62b6370da1629abba1375398)]
- 🚚 : split shared components in subfolders [[8ec8295](https://github.com/gaia-app/gaia/commit/8ec829571063f0ff0aa1d4673202f2d74f5ed187)]
- ♻️ : migrate job page [[02fdbf3](https://github.com/gaia-app/gaia/commit/02fdbf3bf7e2808aa700e5331558fbe3988c19c4)]
- ♻️ : migrate backend for job [[23495b9](https://github.com/gaia-app/gaia/commit/23495b9810a66eb37eb7aa4746041bfb1aaf3651)]
- ⚡ : call api simultaneously on users page [[6c48041](https://github.com/gaia-app/gaia/commit/6c48041bde44fc1bb2b3a4de6a7ae65d5b7b69a5)]
- 💄 : enhance look of user-badge icon [[80c8391](https://github.com/gaia-app/gaia/commit/80c8391f652d3721fe55775c740468ce7c41d62b)]
- ♻️ : migrate stacks page [[e83caf4](https://github.com/gaia-app/gaia/commit/e83caf45c6de61afc529f630f1ec2ba443c99a73)]
- ♻️ : migrate stack edition page [[5a77b98](https://github.com/gaia-app/gaia/commit/5a77b98d7c07cf811c725b31e0b606433bbf255e)]
- 🚚 : rename api service file for stacks [[f5c81dc](https://github.com/gaia-app/gaia/commit/f5c81dcb4adb6d856bf932920a3b11035fd1aed6)]
- ♻️ : migrate users page [[43d65f6](https://github.com/gaia-app/gaia/commit/43d65f6d9040693c4548af78ed6be925e47eba97)]
- ♻️ : migrate settings page [[4bf2d24](https://github.com/gaia-app/gaia/commit/4bf2d2474fa534a10a2f3e90ace35b8a59c20e86)]
- 🔧 : move vue-form-wizard declaration [[e519110](https://github.com/gaia-app/gaia/commit/e5191100aaeeac58992088b1d1894fc8d57b51e3)]
- 🔧 : move vue-multiselect declaration [[f3e9a9c](https://github.com/gaia-app/gaia/commit/f3e9a9c7d1eba2177d4255bd2960c19fb9c501f8)]
- ♻️ : migrate dashboard page [[094f05a](https://github.com/gaia-app/gaia/commit/094f05a254aaf58b8e911a359067de26849186b4)]
- 🍱 : update logo for topbar [[52e934d](https://github.com/gaia-app/gaia/commit/52e934d09a17c07fa824e4b153ade9e8f5c4aabe)]
- ♻️ : extract module-variable component [[0692969](https://github.com/gaia-app/gaia/commit/06929694777e154e9cda741b13ac5e6f9d529c8b)]
- ♻️ : make the import route a POST [[7c621c1](https://github.com/gaia-app/gaia/commit/7c621c16f1ae6983b347b2af42998905880cec50)]
- ♻️ : add stack-api [[ea0f2d1](https://github.com/gaia-app/gaia/commit/ea0f2d1bfbf33212125f59269cba42cb9bcb2071)]
- ♻️ : use markdown component in readme [[03d58c2](https://github.com/gaia-app/gaia/commit/03d58c281fec00a50347ad66a1bf08ae83ba35c4)]
- 💄 : use &lt;font-awesome&gt; component instead of &lt;i&gt; [[c0e1fe0](https://github.com/gaia-app/gaia/commit/c0e1fe0e306065d09cdb96ba8a8f3891090cd7b8)]
- ♻️ : extract api services [[2440473](https://github.com/gaia-app/gaia/commit/2440473c67f57bb8e9e6c0fd773ba9cb425e948a)]
- ♻️ : import markdown css in its dedicated component [[6804875](https://github.com/gaia-app/gaia/commit/6804875160b1104ef6225bd54649d771da46460e)]
- ♻️ : use props instead of $route to get component inputs [[119bb4e](https://github.com/gaia-app/gaia/commit/119bb4e172d033ae1df07380cd27e0c7e54c9d64)]
- ♻️ : move stack creation route to stack pages [[b2b28a3](https://github.com/gaia-app/gaia/commit/b2b28a37e831cca82a142d2c7d4bd985435ea125)]
- ♻️ : migrate new stack page [[40ab014](https://github.com/gaia-app/gaia/commit/40ab01400884d6826638ed13b9e03e467a87275b)]
- ♻️ : migrate module-import page [[17dac15](https://github.com/gaia-app/gaia/commit/17dac158a073165e2f18f5edc6b65b9e866d27a7)]
- ♻️ : migrate module-description page [[3df8a15](https://github.com/gaia-app/gaia/commit/3df8a15b3f9586c65811702b8a154777e3b960ba)]
- ♻️ : migrate the module details page [[6cc3fa3](https://github.com/gaia-app/gaia/commit/6cc3fa3a409bc8cb2ff2da398d004ed9c248c1d2)]
- ♻️ : migrate the modules page [[c1d939e](https://github.com/gaia-app/gaia/commit/c1d939e5602b3dc526ee426c404214ad2294f4a4)]
- ♻️ : rework breadcrumb [[508aad5](https://github.com/gaia-app/gaia/commit/508aad5e22814f3dc0e0b5bd201923a7bf059578)]
- 🚚 : move job page as child of stack page [[c878d0a](https://github.com/gaia-app/gaia/commit/c878d0a1e570e48732e7c3b7bdc96a7f04452147)]
- 🔧 : use alias @ in all import and export paths [[eb6dbbf](https://github.com/gaia-app/gaia/commit/eb6dbbfb692739ec97c018d6b4dad930990d7fae)]
- ♻️ : small rework on login page [[26ca8e5](https://github.com/gaia-app/gaia/commit/26ca8e512dd35087a9ad2a3cb2226c030a203d02)]
- 🚚 : gather error pages in a sub folder [[07a1fee](https://github.com/gaia-app/gaia/commit/07a1fee9438885357a16b44518fb9740257db35c)]
- ♻️ : rework login page [[b4f9f72](https://github.com/gaia-app/gaia/commit/b4f9f72676c12fb31941857068c1881fb03a8c25)]
- ♻️ : rework security process [[7f65585](https://github.com/gaia-app/gaia/commit/7f65585e8ba473b5b5a4b28d9e95aaed731e2d04)]
- 🔧 : configure vue cli dev server with proxy [[45081db](https://github.com/gaia-app/gaia/commit/45081dbbc7893ec9d6f85650ab740420f0617943)]
- 🔧 : specify frontend directory for lint [[cb8d84f](https://github.com/gaia-app/gaia/commit/cb8d84f2a1faf393a4dd55f2093059390c29830f)]
- 🏗️ : init frontend structure and routes [[8d0a71c](https://github.com/gaia-app/gaia/commit/8d0a71c32a9f924d15c61de0831c71c8fa9604ff)]
- 🔧 : configure vue-multiselect [[ae1225d](https://github.com/gaia-app/gaia/commit/ae1225dfe98ec486b739734f7630efd6b15e9a94)]
- 🔧 : configure bootstrap and bootstrap-vue [[fbc11ba](https://github.com/gaia-app/gaia/commit/fbc11bacd55c4cab4eae2fa64b7d858056bb0c85)]
- 🔧 : configure vue-fontawesone [[17eb2a2](https://github.com/gaia-app/gaia/commit/17eb2a2841e9f102a10ba33bca83bf524294713b)]
- 🔧 : add webpack configuration for frontend [[7e18f64](https://github.com/gaia-app/gaia/commit/7e18f644a63edf4dbcf9a7996223adc85f8fcb74)]
- 🔧 : add maven configuration for frontend [[1db14be](https://github.com/gaia-app/gaia/commit/1db14be160118c0bdc2408eb02cdd5d8a0081ee0)]
- 🎨 : add .editorconfig [[7a3c277](https://github.com/gaia-app/gaia/commit/7a3c277689a85606b450a99aaa1dca6191b45608)]
- 🎨 : add .eslintrc [[1f5cc15](https://github.com/gaia-app/gaia/commit/1f5cc1538419a3c27d519b4e562bfbc996740760)]
- 🏗️ : add controller to manage natural routes for frontend [[9f9090c](https://github.com/gaia-app/gaia/commit/9f9090c65b4133ea61513ffaaf0172e1027f5d6a)]
- 🏗️ : init new frontend application [[3c69763](https://github.com/gaia-app/gaia/commit/3c697635b5bbe215ac46af5c0cc2cb7d8f5f02d1)]

### Removed

- ➖ : remove the UUID dependency [[2a40ab8](https://github.com/gaia-app/gaia/commit/2a40ab8025aa21e926772489cba1871d0ca71b48)]
- 🔥 : remove old spring templates and assets [[39b6f8c](https://github.com/gaia-app/gaia/commit/39b6f8c10f02e82fbb60b7842d4b66701afe24b2)]
- 🔥 : remove useless id on vue template [[5ada7af](https://github.com/gaia-app/gaia/commit/5ada7af9ea717d8ba969a84a08de31dcc2b05b48)]
- 🔥 : remove useless users mvc controller [[b3918fa](https://github.com/gaia-app/gaia/commit/b3918fad98787d532965d55724a0ad255c9cfbef)]
- 🔥 : remove index.js from shared api and services [[410503b](https://github.com/gaia-app/gaia/commit/410503bc894822fb2d4eb859ccefa3b07bdfc641)]
- 🔥 : remove ModuleMVCController [[eba9e2b](https://github.com/gaia-app/gaia/commit/eba9e2b083678856aecc274012714df9db0789db)]
- 🔥 : remove useless constructor term in kotlin [[2b737b2](https://github.com/gaia-app/gaia/commit/2b737b2e160ba7d571f4de0eee21228f8c12c24c)]
- 🔥 : remove demo css [[c737c93](https://github.com/gaia-app/gaia/commit/c737c93de54063fbc3dd0d2bb8051a92f27ccc23)]
- 🔥 : remove UiExtensions process [[28f1be3](https://github.com/gaia-app/gaia/commit/28f1be3fe4c4fa23ef19c289d4e54937dae5f836)]
- ➖ : remove webjars dependencies [[b8c40c8](https://github.com/gaia-app/gaia/commit/b8c40c88d28f4ad90df338c68149ae43c215f157)]
- 🔥 : deactivate mvc controllers [[85004d5](https://github.com/gaia-app/gaia/commit/85004d547c82deaeacbd4f6af685efa1bf13e9b7)]
- 🔥 : remove thymeleaf [[7e0e953](https://github.com/gaia-app/gaia/commit/7e0e9533b1cbcd4ad302fda0b640c57fd4c172d9)]

### Fixed

- 🐛 : fix the save and run on stack creation [[0095823](https://github.com/gaia-app/gaia/commit/0095823e938e02de6be3226f80374dab4e16128a)]
- 🐛 : sort jobs by start date desc [[81f7411](https://github.com/gaia-app/gaia/commit/81f7411a4f2a98b85c151f950c08e2daee7b5a6d)]
- 🐛 : fix issues with terraform-image-input component [[6a85e5e](https://github.com/gaia-app/gaia/commit/6a85e5eaf34802945b3938f66a9ccf831d0231ad)]
- 🐛 : fix redirection reload issue to job in stack page [[74f73e4](https://github.com/gaia-app/gaia/commit/74f73e47e25fce1dca6f097388151b11ae373003)]
- 🐛 : fix issue with authentication guard [[579ab0e](https://github.com/gaia-app/gaia/commit/579ab0ec8d8aa57be785476bbe93f710a7c04516)]
- 🐛 : fix stack loading when user has no team [[2f5ba22](https://github.com/gaia-app/gaia/commit/2f5ba2269ab6539515b405243dc66a691ef9ab4c)]
- 🐛 : disable CSRF on state api to allow POST [[118cc40](https://github.com/gaia-app/gaia/commit/118cc40657207b7c9ba66ec7efc386acb1995585)]
- 🐛 : fix missing BadgePlugin declaration [[8e533a7](https://github.com/gaia-app/gaia/commit/8e533a7080f6ff9ca792803813b85e0d1bd2b65b)]
- 🐛 : correct potential NPE on 404 [[be05718](https://github.com/gaia-app/gaia/commit/be057187878704abf5115acfcfad9eaf32df874b)]
- 🐛 : return empty string when file doesn&#x27;t exist [[2c4644b](https://github.com/gaia-app/gaia/commit/2c4644b7835ff941dd752cd803e3e84cc6232c7e)]
- 🐛 : fix issue on page authorities management [[f3e063f](https://github.com/gaia-app/gaia/commit/f3e063f8ab996da2ed978e3a65fefac3a94b59bb)]
- 🐛 : fix error on sidebar when cookie is missing [[0122518](https://github.com/gaia-app/gaia/commit/0122518732160ce04f0a65b9e2c53afa29fa10b6)]
- 🐛 : fix error on authentication at loading [[e5ad303](https://github.com/gaia-app/gaia/commit/e5ad30393dafb5c0012e561684aaa4cdca176270)]

### Security

- 🔒 : users should not leak their oauth2 token through json [[aa9a749](https://github.com/gaia-app/gaia/commit/aa9a749ccd9e3a002d31099ecf92bd4a5ef8b4d1)]

### Miscellaneous

- 👌 : use named routes [[91e5a48](https://github.com/gaia-app/gaia/commit/91e5a48264291f3014c4f6ebc6c4f9ee3e58319a)]
- 👌 : fix await usage [[250441e](https://github.com/gaia-app/gaia/commit/250441ee3bd588611856e3335fe40b42574f3fb7)]
- 🙈 : ignore frontend build output dir [[b10c2bf](https://github.com/gaia-app/gaia/commit/b10c2bffbbfea3b8e577e6cb481dc590e153cc7d)]
- 🙈 : update .gitignore for frontend [[ebb886c](https://github.com/gaia-app/gaia/commit/ebb886c6cb0f3bf46ec5687e52d1179d95d69ca7)]
- 🐳 : fix tag for mongo image [[997808c](https://github.com/gaia-app/gaia/commit/997808ca6e07b7402dcc4d6b4d238f84ea72229c)]
- 📝 : add missing documentation about authorization [[1b819d4](https://github.com/gaia-app/gaia/commit/1b819d45c25c669a2187da56361dccdeb380fd4c)]
- 🔀 : Merge pull request [#235](https://github.com/gaia-app/gaia/issues/235) from binlab/fix/add-missed-documentation [[03fd692](https://github.com/gaia-app/gaia/commit/03fd692542b167f878c9db0bae74d005c9d3f859)]
- 🔀 : Merge pull request [#229](https://github.com/gaia-app/gaia/issues/229) from gaia-app/release/1.3.0 [[9b39ccc](https://github.com/gaia-app/gaia/commit/9b39ccce387321b8068aad4d0101e6de1f017842)]


<a name="1.3.0"></a>
## 1.3.0 (2020-02-13)

### Added

- ✅ : wait for the job title to appear [[d9b52c5](https://github.com/gaia-app/gaia/commit/d9b52c52cb70718a4e5c1c4d9e70afd3309e76e3)]
- ✅ : cover job and stack page with percy [[3c8981a](https://github.com/gaia-app/gaia/commit/3c8981afbd5d7630b2efdb9cfb8e198e3ef90f06)]
- ✨ : use generated user to configure terraform backend [[01cded5](https://github.com/gaia-app/gaia/commit/01cded579c1b2e488734440e4fa17bcf65feace3)]
- ✅ : add tests on TerraformImage [[6c9498c](https://github.com/gaia-app/gaia/commit/6c9498c8e934601881e06577a80a95b4bb6b362f)]
- ✨ : add possibility to delete a job from the job view [[50b5d1e](https://github.com/gaia-app/gaia/commit/50b5d1ee84998758ff35a44fed357d0d7da86e19)]
- ✨ : add service to delete a job [[7449f95](https://github.com/gaia-app/gaia/commit/7449f95081f0ca93333abdc6b09ab95a703764eb)]
- ✨ : add information about image used for jobs [[053d839](https://github.com/gaia-app/gaia/commit/053d8398fcd2a6d92a6fb1051f43dc40c7bee535)]
- ✨ : add vue component to manage custom terraform image [[a141732](https://github.com/gaia-app/gaia/commit/a1417321cbb53e18c3c4da505e19d157e3fa7357)]
- ✨ : add vue component to render typeahead inputs [[4492ebe](https://github.com/gaia-app/gaia/commit/4492ebedc2bb9166bb72011c1322897bd7fed898)]
- ✨ : add resources to find repositories and tags from docker [[b0e486e](https://github.com/gaia-app/gaia/commit/b0e486ea55c355b60d1f638aae9fedaf57e541f2)]
- ✨ : show main provider logo on module description page [[a9bca8e](https://github.com/gaia-app/gaia/commit/a9bca8ecde3816bac0e27b50a554be5c5ab056ad)]
- ✨ : parse provider from module code [[5517dde](https://github.com/gaia-app/gaia/commit/5517dde36a8cfcf955413a0b3d090c6f267a163f)]
- ✅ : rewrite test in kotlin [[3ca6f20](https://github.com/gaia-app/gaia/commit/3ca6f203dd3d7b3d42a5f97c9624110faf9db9e6)]
- ✨ : do not show unnecessary fields [[9474f90](https://github.com/gaia-app/gaia/commit/9474f9046b9affe8d1677e81a393b633bd5870a4)]
- ✨ : add module update metadata [[e0434e9](https://github.com/gaia-app/gaia/commit/e0434e97a4cf4cdc3c4bf16caab8a59a56c856d3)]
- ✨ : add module creation date [[0739025](https://github.com/gaia-app/gaia/commit/07390254efcfae49bc4b081765a9ebc1aea7e726)]
- ✅ : add Gitlab import IT [[7854e1b](https://github.com/gaia-app/gaia/commit/7854e1b8d924741e8c5ebb08de52b9a186b24860)]
- ✅ : add Github import IT [[fe35014](https://github.com/gaia-app/gaia/commit/fe350147f92beec45632ad26106c2c0bf33e9fc9)]
- ✨ : add gitlab import [[3bb96e6](https://github.com/gaia-app/gaia/commit/3bb96e64852e1ed883cf394a5ab126dfefe7b352)]
- 👷 : change sonarcloud configuration [[e110328](https://github.com/gaia-app/gaia/commit/e110328b1a2074231107430ace4bc7301328c620)]
- ✨ : add import module screen [[e2075ed](https://github.com/gaia-app/gaia/commit/e2075edbf37e3dfee75ad639777216516568f847)]
- ✨ : import module from github [[1a6647e](https://github.com/gaia-app/gaia/commit/1a6647e08a99a55890b14305105019b3e668601e)]
- ✅ : corrects test based on mutation tests [[3f55583](https://github.com/gaia-app/gaia/commit/3f555835ecd30f24bfcd4b8140746360801a7afd)]
- ➕ : add pitest configuration [[a96ac1f](https://github.com/gaia-app/gaia/commit/a96ac1f7e27ecb9f0a1c60070500aec67a4ee1ae)]
- 🔊 : log error when a job fails [[8d40396](https://github.com/gaia-app/gaia/commit/8d403963b1e62e9fdfcb42d8320f69761afaff91)]
- ✅ : add simple integration test for DockerRunner [[b728f95](https://github.com/gaia-app/gaia/commit/b728f95ceef79ee0402677e5c8e2dcac2ab26df4)]
- ✨ : manage CSS &amp; JS external dependencies dynamically [[037ec7c](https://github.com/gaia-app/gaia/commit/037ec7c9d6eefd14ef0796b4cd500490bbb73522)]
- 👷 : run percy on master [[0fdfabc](https://github.com/gaia-app/gaia/commit/0fdfabc595edfe6da504a7acc7ae839837c1788a)]
- 👷 : only pull_requests run percy [[05a1655](https://github.com/gaia-app/gaia/commit/05a1655317a08139fc673b7d111715120ff7a5b1)]
- 👷 : switch to firefox for travis [[bbf51f4](https://github.com/gaia-app/gaia/commit/bbf51f471d93b9ae02719b335d749a2b344406ff)]
- 👷 : add chrome addon [[2fcc3b1](https://github.com/gaia-app/gaia/commit/2fcc3b173368fc28829ad285918062e5861c8458)]
- 👷 : integrate percy to travis.yml [[95850f8](https://github.com/gaia-app/gaia/commit/95850f81d69aef47b1987965f188087a6bacc00a)]
- ✅ : integrate percy.io [[e209d9d](https://github.com/gaia-app/gaia/commit/e209d9d37f14059844623f98e5b580d1cc2e9a21)]
- ✅ : add e2e test scenario [[ada0909](https://github.com/gaia-app/gaia/commit/ada0909ef565236d1e36364d7691a6141a9672be)]
- ➕ : add selenium dependency [[a9097c1](https://github.com/gaia-app/gaia/commit/a9097c1641a5d5376566237d7b945af71c8abdb5)]
- ✨ : add HclParser class [[6c2b03c](https://github.com/gaia-app/gaia/commit/6c2b03c0da6e525dc70d8b53004800da9ac8dd07)]
- ✨ : add HCL complex types support [[2c58aa5](https://github.com/gaia-app/gaia/commit/2c58aa5122c701a50cccce663181d009023f6702)]
- ✨ : add comment support in HCL parser [[4df23fa](https://github.com/gaia-app/gaia/commit/4df23fad3e1c484fbcdde760d8b3783d56c487dc)]
- ✨ : add HCL parsing for outputs [[812fe04](https://github.com/gaia-app/gaia/commit/812fe0414264b66d07b2ca080a7a34ef599fcd59)]
- ✨ : add HCL parsing for boolean and number variables [[27e5876](https://github.com/gaia-app/gaia/commit/27e587673a6d679d9562498535ca409164b98ddd)]
- ✨ : add HCL variable parsing [[908ee7c](https://github.com/gaia-app/gaia/commit/908ee7c2e39cf24eded7fb0e2baba97440b3b132)]
- ➕ : add antlr dependency for HCL parser [[d5a1589](https://github.com/gaia-app/gaia/commit/d5a1589cb279e19b68b57f4b1c6b0c1020dc87a0)]
- ✨ : add interval to refresh job history [[75dacc9](https://github.com/gaia-app/gaia/commit/75dacc915825253d22891f92376f4ebac04b9d22)]
- ✨ : use timer component to display durations of job and step [[22918d0](https://github.com/gaia-app/gaia/commit/22918d00119029704ab865a60901db48d10419a2)]
- ✨ : add component to display timer [[1d7acb3](https://github.com/gaia-app/gaia/commit/1d7acb391d84244cc4b286ee5a62315e71b285af)]
- ✨ : fetch README.md using remote registry API [[b15a8ec](https://github.com/gaia-app/gaia/commit/b15a8ec4de4daba2ebe93fe91fab8b5d82b434c3)]
- 👷 : optimize build [[14cada7](https://github.com/gaia-app/gaia/commit/14cada75f0aefda696b1c5db702a506ebfcf169f)]
- ✨ : remove debug mode for job log and hide oauth2 token [[45ed5ec](https://github.com/gaia-app/gaia/commit/45ed5ec71ae16788416072c50e9b865ff7c81b0e)]
- ✨ : manage oauth2 access to clone git projects [[9d8a98c](https://github.com/gaia-app/gaia/commit/9d8a98ca0c3195ef9855be4b18279a534a1705b7)]
- ✨ : link module&#x27;s creation with a user [[9dddbcf](https://github.com/gaia-app/gaia/commit/9dddbcf71ac2abc2bcd58659d82c0e7a9318dd7b)]
- ✨ : add options in login page to choose oauth2 connections [[833f63b](https://github.com/gaia-app/gaia/commit/833f63bb90346eac7dde75044ea9188c5092b31c)]
- ✨ : add strategies to manage oauth2 clients and move them in specific package [[0cbcd07](https://github.com/gaia-app/gaia/commit/0cbcd0701b1632e70097645954e13c1f4274ce7e)]
- ✨ : add configuration to handle oauth2 client connections [[cf25f37](https://github.com/gaia-app/gaia/commit/cf25f378d553c1e336f4dac4fb748f5948e262d0)]
- 👷 : generate XML report for jacoco [[e18cdeb](https://github.com/gaia-app/gaia/commit/e18cdebe0474dbe223b425e1648c7fb35f30e0a3)]

### Changed

- ⬆️ : Bump marked from 0.7.0 to 0.8.0 [[28874a4](https://github.com/gaia-app/gaia/commit/28874a46a12976776ce1fad9964363243cb06d5b)]
- ⬆️ : Bump bootstrap-vue from 2.1.0 to 2.4.0 [[3cfe47a](https://github.com/gaia-app/gaia/commit/3cfe47abba32a02799b8317428954fe702ee192f)]
- 🚨 : fix compiler warnings [[c1650fc](https://github.com/gaia-app/gaia/commit/c1650fc02fac5d509573203119a1d986599cfa74)]
- ♻️ : extract job history into a vue component [[2c72f3f](https://github.com/gaia-app/gaia/commit/2c72f3f11a098b7f1aeca623d28e0193467874ee)]
- 💄 : fix effect on error field in variable part in module page [[7231836](https://github.com/gaia-app/gaia/commit/7231836326d1c75ca5ba3940b4963be021aec98c)]
- 🚚 : move inline function whenever in specific utils file [[ebc1677](https://github.com/gaia-app/gaia/commit/ebc1677cec4d7c2a39da9f04828d2da843eef9e5)]
- ♻️ : replace cliVersion concept by a terraform image in module [[070d66e](https://github.com/gaia-app/gaia/commit/070d66e396d32eaf881c9609af3a699bdfad97c2)]
- 🔧 : add spring  annotation support in kotlin for non open classes [[fff1259](https://github.com/gaia-app/gaia/commit/fff125957ae248cb829c058d53e96ec2fd474a13)]
- 🍱 : update logo [[2f96931](https://github.com/gaia-app/gaia/commit/2f96931531a80e8b2aef66f904ff7d73354e8598)]
- ⬆️ : Bump pitest-junit5-plugin from 0.11 to 0.12 [[6019a4e](https://github.com/gaia-app/gaia/commit/6019a4e2fb04cb5fc66e0906168dbd97436ee61e)]
- ⬆️ : Bump pitest-junit5-plugin from 0.10 to 0.11 [[d8ddd69](https://github.com/gaia-app/gaia/commit/d8ddd690f42227bcb9e0c0cd3bbc68c442094fcd)]
- ⬆️ : Bump pitest-maven from 1.4.10 to 1.4.11 [[25ac835](https://github.com/gaia-app/gaia/commit/25ac8355c0b40128ac694dc92c8eeefb4a0b7499)]
- ⬆️ : Bump openjdk from 11-jdk to 14-jdk [[74975dd](https://github.com/gaia-app/gaia/commit/74975ddcee078efaf3c3f4f57cb2f09ceba5c9fb)]
- 🚚 : gather vue components in a subfolder [[baa754c](https://github.com/gaia-app/gaia/commit/baa754c853a9087258f87d5b74f955719f8bebcc)]
- ♻️ : create specific date time vue filter with vanilly instead of moment [[3f4381a](https://github.com/gaia-app/gaia/commit/3f4381ad2c5e4a3a2cb02d46b9e7457177665f90)]
- ♻️ : replace moment duration by vanilla [[80bff83](https://github.com/gaia-app/gaia/commit/80bff8374dedaadbca7ede0ee7251aa2b37a33d5)]
- ♻️ : create a service for module creation [[dce1a8c](https://github.com/gaia-app/gaia/commit/dce1a8c731ae4a0629aa6ded313fbe5237ff9a87)]
- ♻️ : extract SourceRepository class [[d1384c7](https://github.com/gaia-app/gaia/commit/d1384c7181639268b72962f11bc86c488b922c00)]
- ♻️ : extract ModuleMetadata class [[563ed5a](https://github.com/gaia-app/gaia/commit/563ed5ad283bc6a8f0d7d2c6f1a62718f88279bd)]
- ⬆️ : Bump testcontainers.version from 1.12.4 to 1.12.5 [[9cbd48e](https://github.com/gaia-app/gaia/commit/9cbd48e50cf3d26f5f09952f3a7cea1c62297be5)]
- ⬆️ : Bump antlr4.version from 4.7.2 to 4.8-1 [[f0de4f8](https://github.com/gaia-app/gaia/commit/f0de4f84fe29ae0cb91f7f0d9e94561f537b02c4)]
- ⬆️ : Bump spring-boot-starter-parent [[acadcf2](https://github.com/gaia-app/gaia/commit/acadcf2e68295100bf32793d3d8d33b856ed0056)]
- 🚸 : redirect to module description page after import [[9e57e2d](https://github.com/gaia-app/gaia/commit/9e57e2d6071474c81a7f147906acf6332eb7b77e)]
- ♻️ : extract module import to dedicated components [[d9797e0](https://github.com/gaia-app/gaia/commit/d9797e06c62a41d97ae0920b6c15b435ca970f3a)]
- ♻️ : extract AbstractRegistryApi [[4980b02](https://github.com/gaia-app/gaia/commit/4980b022992fdcbef5d1ea492de83cfbdfe9c1c3)]
- ♻️ : extract API URLs in RegistryType [[ffa7b81](https://github.com/gaia-app/gaia/commit/ffa7b814cb48bc6fba9f5a41e9cf6a95c47a3ee8)]
- ♻️ : replace pattern selection with registry type selection [[4b7736f](https://github.com/gaia-app/gaia/commit/4b7736f8ac5d70ab552884c1c0d1612c3f76f526)]
- ⬆️ : Bump spring-boot-starter-parent [[675cce8](https://github.com/gaia-app/gaia/commit/675cce8329afd3f4e84e28438ee5d3393adeddb4)]
- 🚨 : make attributes private [[86eecd3](https://github.com/gaia-app/gaia/commit/86eecd38b6b601b3fbc451137520a88bb19ce6bc)]
- 🚚 : rename integration test [[c8b7e6e](https://github.com/gaia-app/gaia/commit/c8b7e6eecab61c624c8529282343ae8d76881652)]
- ♻️ : use interfaces instead of implementations [[3324659](https://github.com/gaia-app/gaia/commit/3324659cfe680fb2d1fb670c1ec5472d7fe8314e)]
- ♻️ : move MongoRepositories configuration to the right class [[572ba29](https://github.com/gaia-app/gaia/commit/572ba290b1510e7ee248a0896ade86135f826dad)]
- 💬 : correct error pages titles [[4ceaed3](https://github.com/gaia-app/gaia/commit/4ceaed3264168a78d97781a9cd98e3c4af4ed811)]
- 🚨 : correct depreciation warning [[70103f4](https://github.com/gaia-app/gaia/commit/70103f46df803c6c5e52f482de9fe5a1b8f291aa)]
- 📌 : pins antlr4 version in properties [[c20fc1b](https://github.com/gaia-app/gaia/commit/c20fc1b3eb1e686b7adbe5070f152b1a6753297f)]
- ⬆️ : Bump marked from 0.6.2 to 0.7.0 [[0f7de22](https://github.com/gaia-app/gaia/commit/0f7de220249cb1d799b3663ecec04a7c11ddd692)]
- ⬆️ : Bump bootstrap-vue from 2.0.4 to 2.1.0 [[e4241a6](https://github.com/gaia-app/gaia/commit/e4241a6ec5ef0374715967b60f2bdb986dadbf9e)]
- ⬆️ : Bump font-awesome from 5.8.2 to 5.12.0 [[6c57744](https://github.com/gaia-app/gaia/commit/6c577447efeea93e05ce72690aa6624e56ef3187)]
- ⬆️ : Bump bootstrap from 4.3.1 to 4.4.1 [[53db01d](https://github.com/gaia-app/gaia/commit/53db01d205738fcad03bc9d6bb200884fd2285e7)]
- ⬆️ : Bump vue from 2.5.16 to 2.6.11 [[0cc7f70](https://github.com/gaia-app/gaia/commit/0cc7f707a1fca17352ece45458912c6ba9df064a)]
- 📌 : fixes jersey.version to 2.27 [[0801e9b](https://github.com/gaia-app/gaia/commit/0801e9bbe958dac2b31dbb9d57f2fdebf5f090a5)]
- 💄 : remove useless &quot;online&quot; animation [[603dbef](https://github.com/gaia-app/gaia/commit/603dbef4139336b87ec2af1a7d5c3baf8bb08d40)]
- ♻️ : move common scripts to a include file [[86f63c3](https://github.com/gaia-app/gaia/commit/86f63c340e12e61584560cd3a49e137dce0d8b04)]
- 💄 : add ids to components [[a7a8f25](https://github.com/gaia-app/gaia/commit/a7a8f257823604d0273c95d6b972d6f7a51de52f)]
- 💄 : add types classes to dashboard widgets [[f146f0f](https://github.com/gaia-app/gaia/commit/f146f0fc5312e9b40e9cd50142f83486bd28471a)]
- 🔧 : add kotlin tests compilation [[d45c27f](https://github.com/gaia-app/gaia/commit/d45c27f03cb75e19ed24618cbbc6697887298868)]
- ⬆️ : bump testcontainers.version to 1.12.4 [[11230aa](https://github.com/gaia-app/gaia/commit/11230aae45967e4965fe41fb06b131c5b23c7964)]
- ⬆️ : Bump spring-boot-starter-parent [[ea0ac13](https://github.com/gaia-app/gaia/commit/ea0ac1356e06b7f6929ad21850aef041ca8f17eb)]
- ⬆️ : Bump kotlin.version from 1.3.60 to 1.3.61 [[c4009b5](https://github.com/gaia-app/gaia/commit/c4009b5aac0d3eee11302fd0d71b62db289f80ca)]
- ⬆️ : Bump antlr4-maven-plugin from 4.7 to 4.7.2 [[7f5726f](https://github.com/gaia-app/gaia/commit/7f5726f2e9fd2c46e367bffacf358ea486e11886)]
- ⬆️ : Bump antlr4-runtime from 4.7 to 4.7.2 [[587a4eb](https://github.com/gaia-app/gaia/commit/587a4ebb4a0d137cb5e0bb7009c325a85fabdf9b)]
- ⬆️ : Bump kotlin.version from 1.3.50 to 1.3.60 [[388f5ea](https://github.com/gaia-app/gaia/commit/388f5eaec61ecafbb8df0e62bf56788f65cce407)]
- 🚚 : move Output class to the module package [[2f7b5bc](https://github.com/gaia-app/gaia/commit/2f7b5bcbfdb83ffcdb28b0248e1d51d015f9ec7f)]
- ♻️ : convert TerraformVariable.java to Variable.kt [[27a83c8](https://github.com/gaia-app/gaia/commit/27a83c8a48cf0b21147813024943c8afe35a4afd)]
- 🚨 : rename antlr expression to better code quality [[f077694](https://github.com/gaia-app/gaia/commit/f077694714170b3dfb82159f8914ba988a12aa5e)]
- 🔧 : add antlr maven plugin [[a2c660a](https://github.com/gaia-app/gaia/commit/a2c660a5ff0ab8f1b5ecd1337596ba3de64662eb)]
- ⬆️ : Bump jersey-hk2 from 2.27 to 2.29.1 [[1bd5d04](https://github.com/gaia-app/gaia/commit/1bd5d040cfddc278ec054a5f71a3f2b228747690)]
- ⬆️ : Bump jacoco-maven-plugin from 0.8.4 to 0.8.5 [[9ed8913](https://github.com/gaia-app/gaia/commit/9ed89130a0a6c7dc362703dab25b3ea700c7be2f)]
- ⬆️ : Bump spring-boot-starter-parent [[bcb60f8](https://github.com/gaia-app/gaia/commit/bcb60f8f4f428c1cc0898333d41388a2e924a0ff)]
- ⬆️ : Bump junit-jupiter.version from 5.5.0 to 5.5.2 [[3842197](https://github.com/gaia-app/gaia/commit/384219700c3f0100b0f5f1d9d9bc9c5b1006207e)]
- ⬆️ : Bump junit-jupiter from 1.11.3 to 1.12.3 [[5fe7b5f](https://github.com/gaia-app/gaia/commit/5fe7b5f033a40858a9b5b315dffa57ae21cf0eb7)]
- ⬆️ : Bump testcontainers from 1.11.3 to 1.12.3 [[97ed32b](https://github.com/gaia-app/gaia/commit/97ed32bdcb219f48cb303ebcc8fa9bb3ace972cf)]
- ♻️ : change calculation of the duration of a job [[5847f55](https://github.com/gaia-app/gaia/commit/5847f55b7444503f8d5f7e62cefe691fbadff25f)]
- 💄 : change informations display in job history [[d383cee](https://github.com/gaia-app/gaia/commit/d383cee20d7eec6944b5bf4e30b8aac86e2b1d82)]
- 🔧 : add &quot;repo&quot; scope for github oauth2 configuration [[d496ddf](https://github.com/gaia-app/gaia/commit/d496ddfc37d88cdc74abe95cb97db0407ef5f45e)]
- 🚚 : rename integration tests [[c742236](https://github.com/gaia-app/gaia/commit/c742236a06637a84de22d58501fe3804e26c04f2)]
- ⬆️ : update bootstrap-vue to 2.0.4 [[9bc44a6](https://github.com/gaia-app/gaia/commit/9bc44a69a9d0489926eb75c919bca3e7bce324ef)]
- 🔧 : create spring profile for oauth2 [[75bfd24](https://github.com/gaia-app/gaia/commit/75bfd245d236f160f6440ec7141115b3ecaf4f97)]
- 🚨 : remove useless imports [[13e1db4](https://github.com/gaia-app/gaia/commit/13e1db49794226f26388ff441ed9638da4d3853d)]
- 🚚 : create specific package for security config [[09e61de](https://github.com/gaia-app/gaia/commit/09e61de6e4f9e7457f1f414a1343f4eed1adb5dd)]
- 🔧 : add kotlin plugin [[ce6b7b1](https://github.com/gaia-app/gaia/commit/ce6b7b1c7d4f1036264cf3a2c73c1328ca8a0e61)]

### Removed

- 🔥 : remove unused imports [[b6b0370](https://github.com/gaia-app/gaia/commit/b6b03701f6c7c4bbf44426985636fdfa3b70d4d2)]
- ➖ : remove momentjs dependency [[43da158](https://github.com/gaia-app/gaia/commit/43da1583feac4b851f69dc671efa5cdb079a1334)]
- ➖ : remove moment-duration-format dependency [[429ffb3](https://github.com/gaia-app/gaia/commit/429ffb3c0f3959f97d1cb913b80a907f02d3e8ab)]
- 🔥 : remove unused repository methods [[f0c4c77](https://github.com/gaia-app/gaia/commit/f0c4c77136a8527f07e505f4eab9d46af1300286)]
- 🔥 : remove unused MockMvc [[efa1e9f](https://github.com/gaia-app/gaia/commit/efa1e9fe7620ba590faabc075a92c524d710bfb5)]
- 🔥 : remove useless fonts [[16e6dba](https://github.com/gaia-app/gaia/commit/16e6dba6031bdc687f0a2d9feac99f8e7d374473)]
- ➖ : excluding transitive dependencies for webjars [[3f0a542](https://github.com/gaia-app/gaia/commit/3f0a54224bd309a1655f68d868c3b417257b34ba)]
- 🔥 : remove empty page template [[f5f5b56](https://github.com/gaia-app/gaia/commit/f5f5b56159f45895d50a90f89dccffd74e91c612)]
- 🔥 : remove HCL listener [[7bea678](https://github.com/gaia-app/gaia/commit/7bea678b634fcf89271332da98485c7a4fb053a4)]
- ➖ : removing junit-jupiter dependencies [[16a8b94](https://github.com/gaia-app/gaia/commit/16a8b94ff79ccf4647d9430a0696f2a7a4e5861e)]
- ➖ : remove commons-compress dependency [[460c9de](https://github.com/gaia-app/gaia/commit/460c9de3a56373125950700106bc15af5720a435)]
- 🔥 : remove creation of user in UserControllerAdvice (done in SuccessHandler) [[e0a30bc](https://github.com/gaia-app/gaia/commit/e0a30bc60c704e83cd72802ebbe33f6b982bb404)]

### Fixed

- 🐛 : users should be able to get their states [[a553d1a](https://github.com/gaia-app/gaia/commit/a553d1a8c11ff9db9526236cb488d77275b7ce6b)]
- 🐛 : fix retry action in job edition page [[84d4b2f](https://github.com/gaia-app/gaia/commit/84d4b2fccabd983657e7ca13875b8d10812a335c)]
- 🐛 : fix job using module image instead of its own [[137ffd9](https://github.com/gaia-app/gaia/commit/137ffd900246670738b054cc3aea273dbecb4e80)]
- 🐛 : fix error on import module page for user without oauth2 credentials [[555ff00](https://github.com/gaia-app/gaia/commit/555ff00a23db5bf75054877b48eacf64e234bbef)]
- 🐛 : use real username for module description [[7e4de99](https://github.com/gaia-app/gaia/commit/7e4de99f88485a300d32f68aa0b227fd12937714)]
- 🐛 : make variables editable by default [[c5ca063](https://github.com/gaia-app/gaia/commit/c5ca0635f3e4d655fe8c4048b6eb8787d7e046ca)]
- 🐛 : correct Gitlab url pattern [[d977e8b](https://github.com/gaia-app/gaia/commit/d977e8b16518d8a3043d9390864dcd86e1c129c7)]
- 🐛 : remove line feeds for large README.md files [[42d531c](https://github.com/gaia-app/gaia/commit/42d531c2b902de4d84c013163f69a78e0725ca6a)]
- 🐛 : handle parameter injection in RestTemplate URL [[f831373](https://github.com/gaia-app/gaia/commit/f8313736c35b20af7299de0f7b83df2e7714a3a5)]
- 🐛 : add ui-extensions to login page [[7b593b7](https://github.com/gaia-app/gaia/commit/7b593b7965be54b7b7ad6a05a0bff47d0a6ac4a2)]
- 🐛 : remove html tag from topbar [[a46b1c9](https://github.com/gaia-app/gaia/commit/a46b1c9fcea86e6b9dfd731979432880ba950922)]
- 🐛 : show owned modules on dashboard when user has no team [[b5d0f45](https://github.com/gaia-app/gaia/commit/b5d0f4526b10597b8df1460efb543b317907e450)]
- 🐛 : users should be able to edit their modules [[48368e3](https://github.com/gaia-app/gaia/commit/48368e3f6c0b363d5c1f157365b929e4a7c09e2a)]
- 🐛 : allow trailing coma in arrays [[35345d6](https://github.com/gaia-app/gaia/commit/35345d6bea333930f075537620fd6d4d04de27b5)]
- 🐛 : remove surrounding quotes from parsed HCL [[9092e04](https://github.com/gaia-app/gaia/commit/9092e048c99cd4e629d33634aa2093edfb16236d)]
- 🐛 : allow variable &amp; output fields in any order [[40f650e](https://github.com/gaia-app/gaia/commit/40f650e38be37dbf2f7caa89020c96eb8004263c)]
- 🐛 : fix a bug causing stack to be in a wrong &quot;to update&quot; status [[1d5eb57](https://github.com/gaia-app/gaia/commit/1d5eb570804737a212efbaa6b9cda85190bd3a66)]
- 🐛 : update list regex matching [[6c81e0e](https://github.com/gaia-app/gaia/commit/6c81e0ef352e7bd683f4dc715370ad128450a8c7)]
- 🐛 : update stack variables state on component creation [[c72c1f9](https://github.com/gaia-app/gaia/commit/c72c1f906d6aa4f252f4ea5745af565be79b210a)]
- 🐛 : add volume for docker socket in docker compose [[418c49a](https://github.com/gaia-app/gaia/commit/418c49aa6aa59ed23e5f94f9196128adec278135)]
- ✏️ : fix variables names in README [[06900ef](https://github.com/gaia-app/gaia/commit/06900ef2a1661eb82697c07f4d1d9de4cb7bbf91)]
- 🐛 : fix kotlin data classes deserialization [[28777ae](https://github.com/gaia-app/gaia/commit/28777ae4f5fd4a6d1182df18eb507f1a7cf67463)]
- 🐛 : correct mandatory variables with default value mgmt [[ba0feac](https://github.com/gaia-app/gaia/commit/ba0feacaa1d5687aa2ec05c0b29b3c28840452ff)]
- 🐛 : correct job numbering [[e846485](https://github.com/gaia-app/gaia/commit/e846485c01a4c93f3ae734003048b3b2372f3e5c)]

### Security

- 🔒 : extract state api security configuration [[93302fe](https://github.com/gaia-app/gaia/commit/93302fecbacb93d7c8d0f44331245c7aa321509e)]
- 🔒 : show the right registry for the oauth user type [[c999ddc](https://github.com/gaia-app/gaia/commit/c999ddc1a1eb16dad5b01631ad06f530bd132bec)]

### Miscellaneous

- 📝 : add CONTRIBUTING.md [[ba0bb7e](https://github.com/gaia-app/gaia/commit/ba0bb7e69ff63513faa86c2d5946936b0613f42f)]
- 🔀 : Merge pull request [#222](https://github.com/gaia-app/gaia/issues/222) from gaia-app/dependabot/maven/org.webjars.npm-marked-0.8.0 [[9d5e51d](https://github.com/gaia-app/gaia/commit/9d5e51d3ffdf62aa6b25c9f54e41e727ba4885d6)]
- 🔀 : Merge pull request [#223](https://github.com/gaia-app/gaia/issues/223) from gaia-app/dependabot/maven/org.webjars.npm-bootstrap-vue-2.4.0 [[d67c0c5](https://github.com/gaia-app/gaia/commit/d67c0c522c06e35a9b2055ba8f7a5eeb5b35251b)]
-  Merge pull request [#224](https://github.com/gaia-app/gaia/issues/224) from gaia-app/59-secure-api-state-endpoint [[9f0054b](https://github.com/gaia-app/gaia/commit/9f0054b6d0c1799c71d3c2fa219535110f844e9d)]
- 🔀 : Merge pull request [#221](https://github.com/gaia-app/gaia/issues/221) from gaia-app/121-job-removal [[7c20545](https://github.com/gaia-app/gaia/commit/7c205451d91c32ef19365797b90c4ac770a30d2c)]
- 🔀 : Merge pull request [#220](https://github.com/gaia-app/gaia/issues/220) from gaia-app/183-custom-terraform-image [[2cc3a65](https://github.com/gaia-app/gaia/commit/2cc3a65af16b6761e716249d0354f541db9544c8)]
-  Merge pull request [#219](https://github.com/gaia-app/gaia/issues/219) from gaia-app/update-logo [[9a22672](https://github.com/gaia-app/gaia/commit/9a226721a77735d76af186b7247dd36988153d53)]
- 📝 : update README.md with new logo [[4ddca9f](https://github.com/gaia-app/gaia/commit/4ddca9f9d8a33700c2e4e06da484e6c90ffabb29)]
- 🔀 : Merge pull request [#218](https://github.com/gaia-app/gaia/issues/218) from gaia-app/dependabot/maven/org.pitest-pitest-junit5-plugin-0.12 [[589d4d7](https://github.com/gaia-app/gaia/commit/589d4d7de0e223ee4626891823e0373d59e30a8b)]
- 🔀 : Merge pull request [#199](https://github.com/gaia-app/gaia/issues/199) from gaia-app/dependabot/maven/org.pitest-pitest-junit5-plugin-0.11 [[a7ec58f](https://github.com/gaia-app/gaia/commit/a7ec58fd5b5ae6a1435270e49c8ff4e52c1e8da4)]
- 🔀 : Merge pull request [#200](https://github.com/gaia-app/gaia/issues/200) from gaia-app/dependabot/maven/org.pitest-pitest-maven-1.4.11 [[b6d50ff](https://github.com/gaia-app/gaia/commit/b6d50ff7dc859f4533aa2e3b7255dbc713d1e0ee)]
- 🔀 : Merge pull request [#172](https://github.com/gaia-app/gaia/issues/172) from gaia-app/dependabot/docker/openjdk-14-jdk [[bbd7c88](https://github.com/gaia-app/gaia/commit/bbd7c8823043ca2a332370a605832647e7d54347)]
-  Merge pull request [#217](https://github.com/gaia-app/gaia/issues/217) from gaia-app/216-extract-provider-information [[39820a2](https://github.com/gaia-app/gaia/commit/39820a2e072dd0e0c0dbf725e8b022ceab965b42)]
- 🔀 : Merge pull request [#215](https://github.com/gaia-app/gaia/issues/215) from gaia-app/212-remove-moment-duration [[47a28fa](https://github.com/gaia-app/gaia/commit/47a28fab2153a351a6281bb6387bff5c860f13a9)]
-  Merge pull request [#210](https://github.com/gaia-app/gaia/issues/210) from gaia-app/module-metadata [[af6b3a9](https://github.com/gaia-app/gaia/commit/af6b3a9c26b14a9fd9b46808baac73d3827b79b9)]
-  Merge pull request [#214](https://github.com/gaia-app/gaia/issues/214) from gaia-app/add-codeowners [[10b0be5](https://github.com/gaia-app/gaia/commit/10b0be523d730e7a8351758414bcef2f2ea1b548)]
- 👥 : add CODEOWNERS [[1b73c50](https://github.com/gaia-app/gaia/commit/1b73c50f6878fa6705ab680dcf3b2bcbcbe342a9)]
- 🔀 : Merge pull request [#213](https://github.com/gaia-app/gaia/issues/213) from gaia-app/dependabot/maven/testcontainers.version-1.12.5 [[d892781](https://github.com/gaia-app/gaia/commit/d8927815f9a1c437dd7309a8d8c6c63b625dc6e6)]
- 🔀 : Merge pull request [#207](https://github.com/gaia-app/gaia/issues/207) from gaia-app/dependabot/maven/antlr4.version-4.8-1 [[d07840a](https://github.com/gaia-app/gaia/commit/d07840a98271997f0bdfa9c240119a24c58babd8)]
- 🔀 : Merge pull request [#209](https://github.com/gaia-app/gaia/issues/209) from gaia-app/dependabot/maven/org.springframework.boot-spring-boot-starter-parent-2.2.4.RELEASE [[c18be4b](https://github.com/gaia-app/gaia/commit/c18be4b4511333b46d81a610e7a4fb26c59acea5)]
-  Merge pull request [#208](https://github.com/gaia-app/gaia/issues/208) from gaia-app/module-import-redirect [[e8cfec7](https://github.com/gaia-app/gaia/commit/e8cfec7a210fc46e92a5164c48e5a0b96594de7b)]
-  Merge pull request [#202](https://github.com/gaia-app/gaia/issues/202) from gaia-app/gitlab_module_import [[e505eb5](https://github.com/gaia-app/gaia/commit/e505eb5ab63113131dca5288ca8eb26f8b8bac36)]
- 🔀 : Merge pull request [#203](https://github.com/gaia-app/gaia/issues/203) from gaia-app/dependabot/maven/org.springframework.boot-spring-boot-starter-parent-2.2.3.RELEASE [[317d50c](https://github.com/gaia-app/gaia/commit/317d50cbbcd1f4c14d3fbd24d82c9ee387a07f3c)]
-  Merge pull request [#201](https://github.com/gaia-app/gaia/issues/201) from gaia-app/organization-migration [[6df40b9](https://github.com/gaia-app/gaia/commit/6df40b9f98a4214c76f1715dda6d5798df7b1ddd)]
- 📝 : update pom with new organization URLs [[c562900](https://github.com/gaia-app/gaia/commit/c5629003e962b41d83cf4dbcb1c7a5e40257250b)]
- 📝 : update badges with new organization URLS [[2dcd285](https://github.com/gaia-app/gaia/commit/2dcd2857d5d89cefdb7fce884181c637a591440b)]
-  Merge pull request [#198](https://github.com/gaia-app/gaia/issues/198) from CodeKaio/github_module_import [[131b0e1](https://github.com/gaia-app/gaia/commit/131b0e198d04c1831cdaf5f66151f0dbcdc0534b)]
- 🔀 : Merge pull request [#160](https://github.com/gaia-app/gaia/issues/160) from CodeKaio/dependabot/maven/org.webjars.npm-marked-0.7.0 [[9fa1d6a](https://github.com/gaia-app/gaia/commit/9fa1d6a1216df3541aaa86b6f1759a88e8baa6c2)]
- 🔀 : Merge pull request [#175](https://github.com/gaia-app/gaia/issues/175) from CodeKaio/dependabot/maven/org.webjars.npm-bootstrap-vue-2.1.0 [[b92bf0d](https://github.com/gaia-app/gaia/commit/b92bf0da71b033b29ae7ad37c6ef7023c1a4b325)]
- 🔀 : Merge pull request [#186](https://github.com/gaia-app/gaia/issues/186) from CodeKaio/dependabot/maven/org.webjars-font-awesome-5.12.0 [[7d14b5d](https://github.com/gaia-app/gaia/commit/7d14b5db9d24ba7c5b47444c07668d518fef3e4b)]
- 🔀 : Merge pull request [#179](https://github.com/gaia-app/gaia/issues/179) from CodeKaio/dependabot/maven/org.webjars-bootstrap-4.4.1 [[8a5e2c7](https://github.com/gaia-app/gaia/commit/8a5e2c79bbab62c6ced021a2dee7b57bfc9c64d4)]
-  Merge pull request [#197](https://github.com/gaia-app/gaia/issues/197) from CodeKaio/bug-ui-extensions-on-login-page [[f243423](https://github.com/gaia-app/gaia/commit/f24342317590779e69ca66fabc397025a477f10a)]
- 🔀 : Merge pull request [#191](https://github.com/gaia-app/gaia/issues/191) from CodeKaio/dependabot/maven/org.webjars-vue-2.6.11 [[2e16fd0](https://github.com/gaia-app/gaia/commit/2e16fd044a2b59842ad61c33cac75ed691a34d68)]
-  Merge pull request [#194](https://github.com/gaia-app/gaia/issues/194) from CodeKaio/193-bug-docker-runner [[5d0efb2](https://github.com/gaia-app/gaia/commit/5d0efb20801ea2f70f545090efa8e7ec5e74ee1f)]
-  Merge pull request [#190](https://github.com/gaia-app/gaia/issues/190) from CodeKaio/ui-extensions [[62ef9b1](https://github.com/gaia-app/gaia/commit/62ef9b17c1c7901303878ee7381029e05410bd8e)]
- ⚗️ : trying to ignore build_commit component in percy [[a5b5629](https://github.com/gaia-app/gaia/commit/a5b56299f08058b878702a7c3d9cdd544f485212)]
-  Merge pull request [#188](https://github.com/gaia-app/gaia/issues/188) from CodeKaio/juwit-patch-1 [[c7edd20](https://github.com/gaia-app/gaia/commit/c7edd2077e2f69fad725a6a4b149d17610c6638d)]
-  Merge pull request [#182](https://github.com/gaia-app/gaia/issues/182) from CodeKaio/selenium [[8b813f3](https://github.com/gaia-app/gaia/commit/8b813f35ed22e2fb51bf547deed8bcd0e9eef2bd)]
- 🙈 : ignore .envrc [[69d46cb](https://github.com/gaia-app/gaia/commit/69d46cbce06fdb6f6ab97b550ca8346f3a6f99ed)]
- ⚗️ : add a selenium test to see if it works in travis [[e7d5be1](https://github.com/gaia-app/gaia/commit/e7d5be1341bcbdd0bd693a35ede89f32a5beb07e)]
-  Merge pull request [#180](https://github.com/gaia-app/gaia/issues/180) from CodeKaio/testcontainers-version [[4de0353](https://github.com/gaia-app/gaia/commit/4de03534d5c9c21c11d357d3b6af79fe9bd6ebeb)]
- 🔀 : Merge pull request [#181](https://github.com/gaia-app/gaia/issues/181) from CodeKaio/dependabot/maven/org.springframework.boot-spring-boot-starter-parent-2.2.2.RELEASE [[85d17e4](https://github.com/gaia-app/gaia/commit/85d17e4db269d5422e6533d2a4371dc3b382363b)]
-  Merge pull request [#176](https://github.com/gaia-app/gaia/issues/176) from CodeKaio/dependabot/maven/kotlin.version-1.3.61 [[ddf8c35](https://github.com/gaia-app/gaia/commit/ddf8c3577b53e30c0d1956a13d2bb930d7124ddb)]
-  Merge pull request [#174](https://github.com/gaia-app/gaia/issues/174) from CodeKaio/173-users-should-be-able-to-edit-their-modules [[0224660](https://github.com/gaia-app/gaia/commit/022466050ba429ce3cfda25ef7726286e8711458)]
- 🔀 : Merge pull request [#171](https://github.com/gaia-app/gaia/issues/171) from CodeKaio/dependabot/maven/org.antlr-antlr4-maven-plugin-4.7.2 [[d176e7b](https://github.com/gaia-app/gaia/commit/d176e7bc2a185c88aaa38fb0390a9fa803ed2977)]
- 🔀 : Merge pull request [#170](https://github.com/gaia-app/gaia/issues/170) from CodeKaio/dependabot/maven/org.antlr-antlr4-runtime-4.7.2 [[1293d5b](https://github.com/gaia-app/gaia/commit/1293d5b7ddbd93b5175fc114ba6a0e0d6ad621a4)]
- 🔀 : Merge pull request [#168](https://github.com/gaia-app/gaia/issues/168) from CodeKaio/dependabot/maven/kotlin.version-1.3.60 [[9814076](https://github.com/gaia-app/gaia/commit/9814076fbf3a84ce39926bf5421dbb3a238a91bc)]
-  Merge pull request [#169](https://github.com/gaia-app/gaia/issues/169) from CodeKaio/readme [[6ce85b7](https://github.com/gaia-app/gaia/commit/6ce85b78021a38048e8bbb778b503158216ee9eb)]
- 📝 : improve README.md [[7831b0b](https://github.com/gaia-app/gaia/commit/7831b0b7ac89257e0da66a58096d12ed36e169f8)]
-  Merge pull request [#167](https://github.com/gaia-app/gaia/issues/167) from CodeKaio/hcl-parsing [[d6c79f9](https://github.com/gaia-app/gaia/commit/d6c79f9dff058e5d6033a795e7c653869eb402a1)]
- ⚗️ : add visitor implementation for HCL parser [[a06f7d4](https://github.com/gaia-app/gaia/commit/a06f7d4ae843738248c7e2db4ab1dc1de0cc086c)]
- 🔀 : Merge pull request [#159](https://github.com/gaia-app/gaia/issues/159) from CodeKaio/dependabot/maven/org.glassfish.jersey.inject-jersey-hk2-2.29.1 [[684f941](https://github.com/gaia-app/gaia/commit/684f941a089c6083fdd95c7f90c9a3385b56d98e)]
- 🔀 : Merge pull request [#165](https://github.com/gaia-app/gaia/issues/165) from CodeKaio/dependabot/maven/org.jacoco-jacoco-maven-plugin-0.8.5 [[85ceedc](https://github.com/gaia-app/gaia/commit/85ceedc7b7551ed0957bcefb3659cc6ca529c44a)]
- 🔀 : Merge pull request [#161](https://github.com/gaia-app/gaia/issues/161) from CodeKaio/dependabot/maven/org.springframework.boot-spring-boot-starter-parent-2.2.1.RELEASE [[e1e778b](https://github.com/gaia-app/gaia/commit/e1e778ba3750e058fd5d971db3b4a58e3bf5433f)]
- 🔀 : Merge pull request [#153](https://github.com/gaia-app/gaia/issues/153) from CodeKaio/dependabot/maven/junit-jupiter.version-5.5.2 [[c9af445](https://github.com/gaia-app/gaia/commit/c9af4453502cba007db18d58b1a5e162752cf858)]
- 🔀 : Merge pull request [#154](https://github.com/gaia-app/gaia/issues/154) from CodeKaio/dependabot/maven/org.testcontainers-junit-jupiter-1.12.3 [[00ff825](https://github.com/gaia-app/gaia/commit/00ff825cede477c79da996ad85e845d2dc7bd6d8)]
- 🔀 : Merge pull request [#156](https://github.com/gaia-app/gaia/issues/156) from CodeKaio/dependabot/maven/org.testcontainers-testcontainers-1.12.3 [[548f847](https://github.com/gaia-app/gaia/commit/548f8478d65943643329a84432d896dacce8ae36)]
- 🔀 Merge pull request [#151](https://github.com/gaia-app/gaia/issues/151) from CodeKaio/timer [[29d620e](https://github.com/gaia-app/gaia/commit/29d620efeda7e7d51c2a20ca396bba756827ede4)]
-  Merge pull request [#150](https://github.com/gaia-app/gaia/issues/150) from CodeKaio/readme-content [[9cb9514](https://github.com/gaia-app/gaia/commit/9cb9514cb15cadeaa215b31471e830d55a85708a)]
-  Merge pull request [#149](https://github.com/gaia-app/gaia/issues/149) from CodeKaio/133-list-regex [[a1b5255](https://github.com/gaia-app/gaia/commit/a1b52558cc869de0ac1654c7b3a85a8aea1d944c)]
-  Merge pull request [#148](https://github.com/gaia-app/gaia/issues/148) from CodeKaio/147-fix-module-without-vars [[bcfa477](https://github.com/gaia-app/gaia/commit/bcfa477ca9e0e4cb93004c30b21cde53774193df)]
-  Merge pull request [#145](https://github.com/gaia-app/gaia/issues/145) from CodeKaio/oauth2 [[1efb1f0](https://github.com/gaia-app/gaia/commit/1efb1f09a1f36e48f00627a7a2acc3549f0e666b)]
-  Merge pull request [#144](https://github.com/gaia-app/gaia/issues/144) from CodeKaio/bug-kotlin-jackson [[26e21f4](https://github.com/gaia-app/gaia/commit/26e21f4230c7bd6dd94d1e80ec63871ca87ba93e)]
-  Merge pull request [#142](https://github.com/gaia-app/gaia/issues/142) from CodeKaio/kotlin [[ed1d70c](https://github.com/gaia-app/gaia/commit/ed1d70c91a2cbe99a257b3e566f057570a4b29c0)]
- ⚗️ : use data classes for teams [[36c0408](https://github.com/gaia-app/gaia/commit/36c0408813a9f5498ae5176928d8ad0310a58934)]
-  Merge pull request [#143](https://github.com/gaia-app/gaia/issues/143) from CodeKaio/141-correct-job-numbering [[a8ce6e2](https://github.com/gaia-app/gaia/commit/a8ce6e206fe809ace9feb4190bf9e031bf9d9096)]


<a name="1.2.0"></a>
## 1.2.0 (2019-09-02)

### Added

- 👷 : corrects integration test inclusion [[c49f0e5](https://github.com/CodeKaio/gaia/commit/c49f0e5c0a021b0b5c19dfeeceb91360877bbdd3)]
- 👷 : build docker images on tags [[2b1eb4e](https://github.com/CodeKaio/gaia/commit/2b1eb4ea02af70326491d751c1deee8dc350acfa)]
- ✨ : add the possibility to execute scripts in mongo testcontainer [[4943c60](https://github.com/CodeKaio/gaia/commit/4943c60f16f331955c9098102b94490655829fb1)]
- ✨ : add /admin/info endpoint [[883da5f](https://github.com/CodeKaio/gaia/commit/883da5f15aceb09b1eeb154733c85ca7bdfb8102)]
- ✨ : add version and commit in sidebar footer [[bc9efc8](https://github.com/CodeKaio/gaia/commit/bc9efc8c327c1b5f2bf5241d84b672ba95828411)]
- ✨ : add option to make module variable mandatory [[d2af11b](https://github.com/CodeKaio/gaia/commit/d2af11b0770939b1ff33a0a2ca0187f8c2c5a546)]
- ✨ : add server-side validation for stacks [[eee74f5](https://github.com/CodeKaio/gaia/commit/eee74f5de604d8050afe43a5027550ecd7f19141)]
- ✨ : add server-side validation for modules [[48b5969](https://github.com/CodeKaio/gaia/commit/48b59699225b4621290ffae888fb6056fa26b03d)]
- ✨ : add client-side regex validation for variables [[ad22dee](https://github.com/CodeKaio/gaia/commit/ad22dee693976f45589a85aa43e1b597c113635b)]
- ✨ : add server-side regex validation for stack variables [[a9b27b9](https://github.com/CodeKaio/gaia/commit/a9b27b9c9f6b3cc34b7f4b3c57fee5d7257ba9c0)]
- ✨ : add vue component for job metadata [[b7b5ec6](https://github.com/CodeKaio/gaia/commit/b7b5ec67b4fc6b715f90002f38d830e2f5d02e3a)]
- ✨ : add vue component for job step [[fb630ce](https://github.com/CodeKaio/gaia/commit/fb630ce4ca8ff179e2c9b0cd236dad147278ba39)]
- ✨ : add retry in job workflow [[1b1686f](https://github.com/CodeKaio/gaia/commit/1b1686f22562b59849e3fd3b8de1df5a4b7d57a6)]
- ✨ : add retry button in job page [[d151a52](https://github.com/CodeKaio/gaia/commit/d151a52153b453a1c83bdbddaef64cdb54bae3aa)]
- ✨ : add vue component for job&#x27;s plan application [[5f4e076](https://github.com/CodeKaio/gaia/commit/5f4e07621be335336d197f045ddb94351e42b719)]
- ✅ : add IT tests using scripts with mongo testcontainer [[b9e4442](https://github.com/CodeKaio/gaia/commit/b9e44424f087f8bd5518548f6af36adcbdcf082c)]

### Changed

- 🚸 : add 404 error page [[c40e7c3](https://github.com/CodeKaio/gaia/commit/c40e7c33df74fb3a172b6998356aa8d2e0c23fc0)]
- 🚸 : add 500 error page [[6e8df1b](https://github.com/CodeKaio/gaia/commit/6e8df1b6c0a3b41c9471977e240bba2692e02861)]
- 🚸 : add 403 error page [[319fa56](https://github.com/CodeKaio/gaia/commit/319fa564a71ead0d9d74b50379e233fe1fe2090c)]
- 🚸 : show welcome message for users with no team [[4677629](https://github.com/CodeKaio/gaia/commit/46776292cd4603f2856bd1229bef46528d47414f)]
- 🚸 : add variable validation to stack-vars component [[3e57d77](https://github.com/CodeKaio/gaia/commit/3e57d77ea25943f11910733f1bc952f312a0d2b9)]
- 🚸 : disable save button when stack form is invalid [[e98b2db](https://github.com/CodeKaio/gaia/commit/e98b2db72aa4fdd6fa232f5d8044fe835a25c6be)]
- 🚸 : add variable validation to new-stack [[73348d9](https://github.com/CodeKaio/gaia/commit/73348d9b9222e96662d2b689ca10d7a50e633ab0)]
- 🚸 : add client-side validation to module fields [[d814b76](https://github.com/CodeKaio/gaia/commit/d814b763095ac30664e1291a8e34d849ff090900)]
- 💄 : use columns card group layout [[88ec6fa](https://github.com/CodeKaio/gaia/commit/88ec6fa421bd6e8c1d20236e304919c82e295408)]
- 💄 : add custom login page [[3834fba](https://github.com/CodeKaio/gaia/commit/3834fbaba60dff236c3b2185417f25170c0c6cdf)]
- ♻️ : moves module feature to its own package [[090ab53](https://github.com/CodeKaio/gaia/commit/090ab53eaadb9ad50579c077c44076511274f8fb)]
- ♻️ : moves stack feature to its own package [[3705dee](https://github.com/CodeKaio/gaia/commit/3705dee23d6ad94e7a87e09da006b8c4f0778eb8)]
- ♻️ : moves dashboard feature to its own package [[d0237ec](https://github.com/CodeKaio/gaia/commit/d0237ec75345dcc4af41cb6026854f04f1ed3525)]
- ♻️ : moves settings feature to its own package [[909fb51](https://github.com/CodeKaio/gaia/commit/909fb51d4c54b9f6aca84d8731828dc71037c76a)]
- ♻️ : extract dashboard widget as a component [[522543b](https://github.com/CodeKaio/gaia/commit/522543be47b80deb4188d99c2caa0a45b1262615)]
- ♻️ : use bootstrap-vue b-card component [[2b3d8a1](https://github.com/CodeKaio/gaia/commit/2b3d8a1414ad5b2f5a2d062e8a8d583eb263fe9b)]
- ♻️ : use bootstrap-vue buttons [[86976be](https://github.com/CodeKaio/gaia/commit/86976be83f263e6d69db8ba19ef437862465c1be)]
- ♻️ : use b-button for new_stack wizard [[f84f204](https://github.com/CodeKaio/gaia/commit/f84f204d7ebaf6bf45a197346e1089acbfca9083)]
- ♻️ : use boostrap-vue components for module form [[f7190ff](https://github.com/CodeKaio/gaia/commit/f7190ffabb6b14410a5fd208533db2ca526ca094)]
- ♻️ : make the sidebar a vue component [[008a029](https://github.com/CodeKaio/gaia/commit/008a029a789692cadea63e5ed8de8b37d5a5bed7)]
- ♻️ : refactor job by adding workflow management [[57f53f3](https://github.com/CodeKaio/gaia/commit/57f53f31a32335e54bc533df380b8f58bfec4aae)]
- ♻️ : refactor stack page considering new job workflow [[1cc8526](https://github.com/CodeKaio/gaia/commit/1cc85269d7bd16828a71576b2cdabe18e4cf6b26)]
- ♻️ : refactor job page considering new workflow [[fb58bed](https://github.com/CodeKaio/gaia/commit/fb58bed6a7b6871c3ba6ff97b5f063cb4ded1acc)]
- ♻️ : refactor new stack page considering new job workflow [[9b40b8d](https://github.com/CodeKaio/gaia/commit/9b40b8d76550fa4e8465c317eb04e7cc16a035a4)]
- ♻️ : refactor state classes to use default method interfaces [[d283198](https://github.com/CodeKaio/gaia/commit/d283198a7f72c402d6049386ccde72a4f6823eaf)]
- 🚨 : remove code duplication [[7bfbd9a](https://github.com/CodeKaio/gaia/commit/7bfbd9ae4b0ffb3215860ba0f86ca64ef9ae54d0)]
- 🚨 : remove unused imports [[bc7d3a2](https://github.com/CodeKaio/gaia/commit/bc7d3a23ad1f843153006518d9a4adf17ed40174)]
- 🚚 : move favicon to static files [[3aced84](https://github.com/CodeKaio/gaia/commit/3aced84e3b65942b33587ef91cfa6f482b7d876e)]

### Removed

- 🔥 : remove old terraform backend structure [[3c9ef89](https://github.com/CodeKaio/gaia/commit/3c9ef89816e9abd10563ecceef2a001bc91ad6d0)]

### Fixed

- 🐛 : correct module &amp; stack count for users without teams [[f9cafa1](https://github.com/CodeKaio/gaia/commit/f9cafa1885f351efab144e226a852853bd4c4749)]
- 🐛 : correct module card description [[d8dc694](https://github.com/CodeKaio/gaia/commit/d8dc694e0062828cdbdf30320bcb874e410403ff)]
- 🐛 : fix variable add/remove [[7a4e6ef](https://github.com/CodeKaio/gaia/commit/7a4e6ef66c1f351ca4b4f44db9ad52a54fa14f98)]
- 🐛 : fix vue warn message [[8dd43aa](https://github.com/CodeKaio/gaia/commit/8dd43aaf0b7a29eb571b96077705a73cd394bfbe)]
- 🐛 : corrects NPE on sidebar when build information is not available [[eaefe72](https://github.com/CodeKaio/gaia/commit/eaefe721282808af6973c1c0ba0b74a5ac1c55c2)]
- 🐛 : keep sidebar status change changing route [[0b1643b](https://github.com/CodeKaio/gaia/commit/0b1643b280b0dc2187379853426c603e5db2ba87)]
- 🐛 : replace anchors with buttons in component console [[fb5d366](https://github.com/CodeKaio/gaia/commit/fb5d36660c62f2c29c7f863749ebba3a69a59958)]
- 🐛 : fix js error when job status is null [[9a65016](https://github.com/CodeKaio/gaia/commit/9a65016b2751f30abef01412877a34bd7e5ec42a)]
- 💚 : use mongodb 4.0 version [[3725856](https://github.com/CodeKaio/gaia/commit/3725856c96726983a9582888456de344b540b61f)]

### Security

- 🔒 : expose public resources [[fe582f7](https://github.com/CodeKaio/gaia/commit/fe582f78a0db09afb05d15d977f38da173b51617)]

### Miscellaneous

- ⚗️ : show list component for list-alike regex validated variables [[aabf795](https://github.com/CodeKaio/gaia/commit/aabf79507194efb659b5c2eb4fa0d3a40438d523)]
- 📝 : add information related to scm, license &amp; ci [[c30568e](https://github.com/CodeKaio/gaia/commit/c30568eb071986cb989f39ff874448549b4e0f9d)]
- 🙈 : update dockerignore [[a6afc29](https://github.com/CodeKaio/gaia/commit/a6afc291dc847f77d4128e95953915f26168ef52)]
- 🐳 : add scripts to initiate mongo database [[3aff994](https://github.com/CodeKaio/gaia/commit/3aff9941c93e7eb2420991ad456fe211b178ca11)]


<a name="1.1.0"></a>
## 1.1.0 (2019-08-09)

### Added

- 👷 : update sonar config [[ec5d7d2](https://github.com/CodeKaio/gaia/commit/ec5d7d23b5389ea62787e0a20bfb96ce24d77077)]
- 👷 : add cache for maven dependencies [[9c5022c](https://github.com/CodeKaio/gaia/commit/9c5022c1d30b1cd8b4f3b00cd73629a78efb7c44)]
- 👷 : remove depth flag [[1ab94a0](https://github.com/CodeKaio/gaia/commit/1ab94a05899cee4e41dbf21a025d3fef186ee78b)]
- 👷 : add discord webhook [[7930fa1](https://github.com/CodeKaio/gaia/commit/7930fa156bc9b01340e68292e795df8a4727e810)]
- ➕ : add momentjs library for date management [[f7af2ea](https://github.com/CodeKaio/gaia/commit/f7af2ea60e577b31448de1b1310e3759aef6ea7a)]
- ➕ : add marked library for markdown to html rendering [[e79dacb](https://github.com/CodeKaio/gaia/commit/e79dacb9b2ff52127eefbc0c02115e505bb7afdb)]
- ➕ : add vue-multiselect dependency [[cef6e30](https://github.com/CodeKaio/gaia/commit/cef6e30a32b1db9f301e36155a8432c03c89efe6)]
- ✨ : add healthcheck [[671796c](https://github.com/CodeKaio/gaia/commit/671796c3891fc12ae4ab43c24fa2ed62dee41e04)]
- ✨ : add type for job [[8086666](https://github.com/CodeKaio/gaia/commit/8086666ea5a7e3071e10413e82765d5a9b2a9e7c)]
- ✨ : add terraform version management for modules [[1a3c399](https://github.com/CodeKaio/gaia/commit/1a3c3996779529863b30e0daa2c10455a3022df4)]
- ✨ : add api to list terraform releases [[bcd4c75](https://github.com/CodeKaio/gaia/commit/bcd4c75e8c4bd6947bd812ec86069abf099561cf)]
- ✨ : add confirmation on preview and run actions when unsaved modifications [[a4ca9a1](https://github.com/CodeKaio/gaia/commit/a4ca9a1ec59969c73a882e0cb848c50df1f25e61)]
- ✨ : add ws to get README file url of a module [[4d60c7d](https://github.com/CodeKaio/gaia/commit/4d60c7d87f991e94ac008ad9e02c26b65f411505)]
- ✨ : create vue component to display README file [[9a34caa](https://github.com/CodeKaio/gaia/commit/9a34caa4b55d7352b9b788de098a464a9e20b251)]
- ✨ : add module description page [[58735ee](https://github.com/CodeKaio/gaia/commit/58735ee052ca919aef4fe56891727c64e058fa82)]
- ✨ : add navigation on each page [[3c3e9c7](https://github.com/CodeKaio/gaia/commit/3c3e9c79c65ff595b5c7c36d8c4688bf5353cf38)]
- ✨ : add vue component to manage navigation [[87249eb](https://github.com/CodeKaio/gaia/commit/87249ebc428152f82a124e8099c761ba2fae2b04)]
- ✨ : add vue component to display logs [[ea146e0](https://github.com/CodeKaio/gaia/commit/ea146e044b81d7a803c415066ff35430184d829e)]
- ✨ : save job once it is started [[bd6f2c4](https://github.com/CodeKaio/gaia/commit/bd6f2c4e2c095e5f8474dfbacd2123c7b6d36e24)]
- ✨ : add execution time for jobs [[b467dea](https://github.com/CodeKaio/gaia/commit/b467dea931db8916efd51e04ac9b48941c3440bd)]
- ✨ : teams &amp; users mgmt [[bc0ad79](https://github.com/CodeKaio/gaia/commit/bc0ad79f61023b348927164e15d0223828cb4106)]
- ✨ : add module authorized teams selection [[cfcf55c](https://github.com/CodeKaio/gaia/commit/cfcf55c3c91e074e160567b0e731fe87c5ea9bd2)]
- ✨ : add users mgmt screen [[83228e3](https://github.com/CodeKaio/gaia/commit/83228e332bee34cf8a339b77da628585bc961d4a)]
- ✨ : add cost mgmt informations for modules [[a3ef489](https://github.com/CodeKaio/gaia/commit/a3ef4899fcb99ec52bced71639db6987f7ed87e9)]
- ✨ : add cost estimation for stacks [[f373ae9](https://github.com/CodeKaio/gaia/commit/f373ae973dcddc561968abf953d2c30324a759ac)]
- ✨ : add a vue component to display user badge [[0cfe94f](https://github.com/CodeKaio/gaia/commit/0cfe94f686b624044a11f686e1bf7b15aeed6e95)]
- ✨ : add user information in stack lifecycle [[4b5e74b](https://github.com/CodeKaio/gaia/commit/4b5e74b67d90d9fe125b592170b06934094dcf7e)]
- ✨ : add user information in job lifecycle [[2a7be79](https://github.com/CodeKaio/gaia/commit/2a7be79640983cdac04de7fb940902ddd13ae54f)]
- ✨ : display creation and update users in stack screen [[81809ce](https://github.com/CodeKaio/gaia/commit/81809ce856fa7dfe278643f735e0d7d31acbbe5a)]
- ✅ : add plan and stop tests [[b997ab5](https://github.com/CodeKaio/gaia/commit/b997ab555c545a85c5b636aff72d9ae5b4f5867e)]
- ✅ : add more tests for stack controller [[a2ecf70](https://github.com/CodeKaio/gaia/commit/a2ecf70cb3519b93a1b5d06b5ed7a0404fa21ff4)]

### Changed

- ⬆️ : update spring-boot-starter-parent version [[8b61e6e](https://github.com/CodeKaio/gaia/commit/8b61e6e45cb3f34103577e1f115a76ea37c7a871)]
- ⬆️ : update junit-jupiter version [[c9c9f96](https://github.com/CodeKaio/gaia/commit/c9c9f96c463d3a21f105310683f54ade1695b64c)]
- 🎨 : reorganize module form [[e8f74fc](https://github.com/CodeKaio/gaia/commit/e8f74fcacd8c43b4c70d8c628a283fa463203ad8)]
- 🚸 : use TF_IN_AUTOMATION env var [[00f3ce8](https://github.com/CodeKaio/gaia/commit/00f3ce8aa7ef3a57c3e42b51fedf75fc902b0d1d)]
- 💄 : change bootstrap css class for module screen [[52065e0](https://github.com/CodeKaio/gaia/commit/52065e041404e9f93a792595b4501d9eeece8913)]
- 💄 : add favicon [[bbfee03](https://github.com/CodeKaio/gaia/commit/bbfee03bc163844d860aad91ea17c9a55c7693cd)]
- ♻️ : refactor StackRunner [[02f7f45](https://github.com/CodeKaio/gaia/commit/02f7f45c0b1f42b20d271d65a4ca732d040bd5ec)]
- ♻️ : create vue component to manage cli badge [[33c2b5d](https://github.com/CodeKaio/gaia/commit/33c2b5db6cb6be0b0bf882e4161b80e8c7bbc80b)]
- ♻️ : clean small code [[e3fac2d](https://github.com/CodeKaio/gaia/commit/e3fac2da9b8ead524fa0eaec0d9955911ecdfc7b)]
- ♻️ : use vue components for module cards [[fa13411](https://github.com/CodeKaio/gaia/commit/fa13411655ef939b1754a1b80ef40f0f68c82651)]
- 🚨 : correct blocker sonar issues [[11b57eb](https://github.com/CodeKaio/gaia/commit/11b57eb7d1ab02e8bb3f01bf3ef2a51e22bfc081)]
- 🔧 : configurable admin password [[cab52cc](https://github.com/CodeKaio/gaia/commit/cab52ccf3948253af1e586b7d060fce12eb2d7f5)]
- 🔧 : make settings persistent [[276d7c8](https://github.com/CodeKaio/gaia/commit/276d7c836a9d7330d5363a78fcfefc48e60d108a)]

### Removed

- 🔥 : remove prism.css [[e6412b3](https://github.com/CodeKaio/gaia/commit/e6412b34ff1c73e4094a5db61ee204022d3cc42b)]
- ➖ : removing unused ace-builds dependency [[a667edb](https://github.com/CodeKaio/gaia/commit/a667edbea756afe3dce06ab6e8c78f35d0bf1b88)]

### Fixed

- 🐛 : fix link redirection behind gaia logo on sidebar [[3f9551c](https://github.com/CodeKaio/gaia/commit/3f9551c3731ccaec64810c9f8b227cdcb49313c7)]
- 🐛 : manage outputs depending on the version of CLI [[ff38b97](https://github.com/CodeKaio/gaia/commit/ff38b978e48e11cfd2dc0c3f3b3f0dac0b44d479)]
- 🐛 : fix css side-effect on markdown rendering [[4422174](https://github.com/CodeKaio/gaia/commit/442217486626451e422e800ba3abc0c335c27986)]
- 🐛 : fix non final versions in cli list [[862652c](https://github.com/CodeKaio/gaia/commit/862652c475ed7fff4edeb8045850fe7a35c0a265)]
- 🐛 : hide empty execution time when job in progress [[66a8a18](https://github.com/CodeKaio/gaia/commit/66a8a18e55a0535a7788fdc33393e9ba5ff2c563)]

### Miscellaneous

- 📝 : add issue &amp; bug report templates [[7dec490](https://github.com/CodeKaio/gaia/commit/7dec4905a3b9828034905e136dbe94babda763ab)]


<a name="1.0.0"></a>
## 1.0.0 (2019-07-17)

### Added

- 👷 : add .travis.yml [[59ba016](https://github.com/CodeKaio/gaia/commit/59ba016b3271e3f6be6f7ed1973928504992d8c5)]
- 👷 : add sonarcloud integration [[e42b1de](https://github.com/CodeKaio/gaia/commit/e42b1de2d400b84d64c8dd341fc73df8b62411a9)]
- ➕ : add junit-jupiter-engine [[4042104](https://github.com/CodeKaio/gaia/commit/4042104d31743798fe66461acf01f312607d94f0)]
- ➕ : add bootstrap-vue dependency [[c0e3d2e](https://github.com/CodeKaio/gaia/commit/c0e3d2e47e2a32b55287dcbea0169749ac31f27b)]
- ✨ : add modules mgmt [[57027f2](https://github.com/CodeKaio/gaia/commit/57027f214c8f8755ef00d99c5e47b0f35f6726bb)]
- ✨ : add state mgmt api [[b947483](https://github.com/CodeKaio/gaia/commit/b947483d1dedc2239d5b8d2f6bfd6df74167cf2c)]
- ✨ : add stack instanciations [[f6fd8d5](https://github.com/CodeKaio/gaia/commit/f6fd8d5ee1053507d42cae0ef915132f845c63af)]
- ✨ : add stack mgmt [[0e8a14d](https://github.com/CodeKaio/gaia/commit/0e8a14df1bb33b33267dedc3c81bf34afdaaa5fa)]
- ✨ : add default value for external_url [[e38ce9b](https://github.com/CodeKaio/gaia/commit/e38ce9bbac0a2081702edfa10f9ba42eff8c7fea)]
- ✨ : settings- add external url mgmt [[57c40d8](https://github.com/CodeKaio/gaia/commit/57c40d88ba727690955fda259af1f1efb0967b75)]
- ✨ : add envVars mgmt [[72e2d63](https://github.com/CodeKaio/gaia/commit/72e2d63684b4db85e0d3df974e1525b5e15e6fa9)]
- ✨ : add stack state information [[c09dff0](https://github.com/CodeKaio/gaia/commit/c09dff0d1b936849be3c8cf1c9752d5f64812ebe)]
- ✨ : save stack status on job completion [[d8098f3](https://github.com/CodeKaio/gaia/commit/d8098f34e65773f6cd0f607a3bcdbb9dc6603062)]
- ✨ : save job after completion [[0db25e5](https://github.com/CodeKaio/gaia/commit/0db25e5c25761d949eacd2c8efd9b3496eae3063)]
- ✨ : change stack state when modifying it [[471ce87](https://github.com/CodeKaio/gaia/commit/471ce873d72a9960fe0ade8570883f515e4eef94)]
- ✨ : add job history [[62591bd](https://github.com/CodeKaio/gaia/commit/62591bd9db5c34487fd37858259a89a839299d71)]
- ✨ : add preview jobs [[9b2694b](https://github.com/CodeKaio/gaia/commit/9b2694ba5ee7a0a0ce9315c2d92d0e5d8ddcc822)]
- ✨ : add stack information on index [[c714098](https://github.com/CodeKaio/gaia/commit/c714098236ad5473267d03a6a512461d2ab25995)]
- ✨ : add docker runner configuration [[c5295b7](https://github.com/CodeKaio/gaia/commit/c5295b76d2db21a8cf25fe6233e6fd0d19ab429b)]
- ✨ : add output block on stack view [[345df9c](https://github.com/CodeKaio/gaia/commit/345df9c41f002a7e0d92ccd132a079c3f0bf49a7)]
- ✨ : editable variables [[07a938d](https://github.com/CodeKaio/gaia/commit/07a938d473519d1e0a5c4125453e29c3800b4c93)]
- ✨ : add description to a stack [[70d7ce9](https://github.com/CodeKaio/gaia/commit/70d7ce9af925692e22a6eb77612840c60204d704)]
- ✨ : add stop job [[c14ce7d](https://github.com/CodeKaio/gaia/commit/c14ce7d0607803b7fe4730613e5be640dc3584f3)]
- ✨ : display job history from the most recent to the least recent [[eceef2e](https://github.com/CodeKaio/gaia/commit/eceef2eaa642f4b52a2731dfb7ea4f67dd7ba395)]
- ✨ : configure template engine for .vue files [[2a2d419](https://github.com/CodeKaio/gaia/commit/2a2d4190185ce8fc751f4332e6fbfbbf7ec42035)]
- ✨ : new stack [[c444026](https://github.com/CodeKaio/gaia/commit/c444026c2a7b6b05f6483130a33e167e9e49cd20)]
- ✨ : add confirm message before stopping a stack [[05e2fda](https://github.com/CodeKaio/gaia/commit/05e2fdad82e034e6160bc304f98cb4ffa550e170)]
- 🎉 : add empty README.md to init repository [[a1adc4c](https://github.com/CodeKaio/gaia/commit/a1adc4c8f10baee90ed6be48c65abd1963234255)]
- ✅ : use test containers [[b9d3fb8](https://github.com/CodeKaio/gaia/commit/b9d3fb8a085e52e319e513bf7b18ffb4600be764)]
- ✅ : add tests for envVars [[b77c5e9](https://github.com/CodeKaio/gaia/commit/b77c5e9642e5b903e52e21c772ff4efe5faaf36f)]
- ✅ : fix auto-approve option testing [[c3310e4](https://github.com/CodeKaio/gaia/commit/c3310e4750bdf6274b48e0d9af8679740d6b7363)]

### Changed

- ⬆️ : updating to terraform 0.12.3 [[76b5d5a](https://github.com/CodeKaio/gaia/commit/76b5d5ae9706574ba75574a2776954e613b11456)]
- 🚸 : add update button [[ff2977c](https://github.com/CodeKaio/gaia/commit/ff2977c9ba77024b8508d6dc153b58f394668595)]
- 💄 : add nice ui [[9b18891](https://github.com/CodeKaio/gaia/commit/9b18891b2ab3e735430fa1b3cc70ddfd8a696325)]
- 💄 : make logs look like logs [[277046c](https://github.com/CodeKaio/gaia/commit/277046ce90830db8e96ce71549fff59f755a49c9)]
- 💄 : cleanup css styles [[9f9147e](https://github.com/CodeKaio/gaia/commit/9f9147ead46d55d987873e4706e49ac4d2ac265d)]
- 💄 : remove backend and provider conf [[c76ad77](https://github.com/CodeKaio/gaia/commit/c76ad77c818ccea03aafba8b0245cf8112139b63)]
- 💄 : show vars and job history on one line [[c610698](https://github.com/CodeKaio/gaia/commit/c610698f3f1bda1aecdaac2a527767eec9ca66ed)]
- 💄 : correct topbar height [[8141037](https://github.com/CodeKaio/gaia/commit/8141037f8c3f222c6ff478c6cc623ccba2ccd761)]
- 💄 : add toast notifications [[4b3b817](https://github.com/CodeKaio/gaia/commit/4b3b817d4546dc5988c5eda5d40b65bada8de254)]
- ♻️ : extract command builder [[23db361](https://github.com/CodeKaio/gaia/commit/23db361fdf9d0422a99de9d9159349c011a620da)]
- ♻️ : use vue for stack list [[63efc78](https://github.com/CodeKaio/gaia/commit/63efc78636b5f85a2f51cf3a46f1fe99b9253b1e)]
- ♻️ : create mustache template to manage terraform scripts [[0784d3b](https://github.com/CodeKaio/gaia/commit/0784d3bfb8518f04c9e930b3351a4ffbd357f491)]
- ♻️ : extract stack-vars vue component [[6efd602](https://github.com/CodeKaio/gaia/commit/6efd602002ed0f7af1203dc0173ee65ca794fa88)]
- ♻️ : extract test data creation [[5e2e310](https://github.com/CodeKaio/gaia/commit/5e2e3104fd1c35d1ed59758b709641a76ac56e7e)]
- ♻️ : stack-vars component updates stack state [[355e5a0](https://github.com/CodeKaio/gaia/commit/355e5a0b68401723d46f5ecea6cadc5fff83a11c)]
- 🔧 : expose all actuators endpoints [[7dd0812](https://github.com/CodeKaio/gaia/commit/7dd0812c112b0a00a39735f5fbfa0f823b899042)]
- 🔧 : add custom property for mongo configuration [[03d8c48](https://github.com/CodeKaio/gaia/commit/03d8c4827378629900ca0a8b0532dd67f527f37c)]

### Removed

- 🔥 : remove duplicate scripts import [[678e712](https://github.com/CodeKaio/gaia/commit/678e712bb77f1a1d8f4864e8d3f90c81c18f46cc)]
- 🔥 : clean-up stack screen [[1f61458](https://github.com/CodeKaio/gaia/commit/1f61458139d7c178501085f43a86c0a7a02f30be)]
- 🔥 : remove test data [[7b68cca](https://github.com/CodeKaio/gaia/commit/7b68cca933a64d0dcb855870d37c252b371b1e68)]

### Fixed

- 🐛 : correct logs for failed jobs [[21250ea](https://github.com/CodeKaio/gaia/commit/21250ea6c2594dc5507fea970ead0d8f1c0a4a82)]
- 🐛 : use module git directory [[51815b4](https://github.com/CodeKaio/gaia/commit/51815b4564d46281cab6529825cafe8cbf5b7ec9)]
- 🐛 : fix stack loading for terraform 0.12 [[b13f4b5](https://github.com/CodeKaio/gaia/commit/b13f4b5c434184d34811f7de2076b87e5b7383b4)]
- 🐛 : fix states of stack in certain conditions [[6a962fc](https://github.com/CodeKaio/gaia/commit/6a962fc8de74bdd7f54bd174f44d8f4e813f976c)]
- 🐛 : add default values for security config [[2a4b4dd](https://github.com/CodeKaio/gaia/commit/2a4b4ddf0c1270885895e1d5d9b5403c06094d2f)]
- 💚 : fixing gaia mongo uri in tests [[10718cb](https://github.com/CodeKaio/gaia/commit/10718cb9efa2b4b77b7d9b8c9cb467b0fd66fe1d)]

### Security

- 🔒 : add ldap authentication [[72f54c3](https://github.com/CodeKaio/gaia/commit/72f54c3fc3bcae3b98498a9ae8726e30402e7a3c)]
- 🔒 : add basic role security [[e0fb634](https://github.com/CodeKaio/gaia/commit/e0fb634de0424052e1ca506049a4dde250041336)]
- 🔒 : add sample user account for tests [[4c31f0f](https://github.com/CodeKaio/gaia/commit/4c31f0f86e92fad17f77b4f3f6769799b4c880c4)]

### Miscellaneous

- 📝 : add LICENSE [[11b6dc6](https://github.com/CodeKaio/gaia/commit/11b6dc6eaa60049ad3dfb53556755da6e9e2849d)]
- 📝 : add ci badges on README.md [[b1ab858](https://github.com/CodeKaio/gaia/commit/b1ab85862bac81bb6561885b54f1e3345ebf14e5)]
- 🙈 : add .gitignore [[95edacd](https://github.com/CodeKaio/gaia/commit/95edacd334db1c3f7c07a2188afe7c8e87f6fb5a)]
- 🐳 : use latest terraform image [[16c8a85](https://github.com/CodeKaio/gaia/commit/16c8a85f9d21eef1cad70f7041ea063d1f3c2560)]
- 🐳 : rollback to terraform image 0.11.14 [[5beb962](https://github.com/CodeKaio/gaia/commit/5beb96294589b93b203e9ae001d91f6e4af92672)]
- 🐳 : add Dockerfile [[f189f07](https://github.com/CodeKaio/gaia/commit/f189f0716630ae32160f2bc4af85ca71c177cafe)]


