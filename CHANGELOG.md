# Changelog

<a name="1.1.0"></a>
## 1.1.0 (2019-08-09)

### Added

- 👷‍♂️ : update sonar config [[ec5d7d2](https://github.com/CodeKaio/gaia/commit/ec5d7d23b5389ea62787e0a20bfb96ce24d77077)]
- 👷‍♂️ : add cache for maven dependencies [[9c5022c](https://github.com/CodeKaio/gaia/commit/9c5022c1d30b1cd8b4f3b00cd73629a78efb7c44)]
- 👷‍♂️ : remove depth flag [[1ab94a0](https://github.com/CodeKaio/gaia/commit/1ab94a05899cee4e41dbf21a025d3fef186ee78b)]
- 👷‍♂️ : add discord webhook [[7930fa1](https://github.com/CodeKaio/gaia/commit/7930fa156bc9b01340e68292e795df8a4727e810)]
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

- 👷‍♂️ : add .travis.yml [[59ba016](https://github.com/CodeKaio/gaia/commit/59ba016b3271e3f6be6f7ed1973928504992d8c5)]
- 👷‍♂️ : add sonarcloud integration [[e42b1de](https://github.com/CodeKaio/gaia/commit/e42b1de2d400b84d64c8dd341fc73df8b62411a9)]
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


