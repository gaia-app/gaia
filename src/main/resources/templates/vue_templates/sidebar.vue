<template id="sidebar-template">
    <nav id="sidebar" th:class="${sidebar_collapsed ? 'active' : ''}">
        <div>
            <div class="sidebar-part">
                <div class="sidebar-header">
                    <div class="logo_section">
                        <a href="/"><img class="logo_icon img-responsive" src="/images/gaia.png" alt="#"></a>
                    </div>
                </div>
                <div class="sidebar_user_info">
                    <div class="icon_setting"></div>
                    <div class="user_profle_side">
                        <div class="user_img"><img class="img-responsive" src="/images/gaia.png" alt="#"></div>
                        <div class="user_info">
                            <h6>Gaia</h6>
                            <p><span class="online_animation"></span> Online</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="sidebar-part">
                <h4>Menu</h4>
                <ul class="list-unstyled components">
                    <li class="active"><a href="/" ><i class="fas fa-tachometer-alt yellow_color"></i> <span>Dashboard</span></a></li>
                    <li><a href="/modules"><i class="fa fa-object-group blue1_color"></i> <span>Modules</span></a></li>
                    <li><a href="/stacks"><i class="fas fa-layer-group blue2_color"></i> <span>Stacks</span></a></li>
                    <li sec:authorize="hasRole('ADMIN')"><a href="/settings"><i class="fa fa-cog yellow_color"></i> <span>Settings</span></a></li>
                    <li sec:authorize="hasRole('ADMIN')"><a href="/users"><i class="fas fa-user-friends yellow_color"></i> <span>Users</span></a></li>
                </ul>
            </div>
        </div>
        <div class="sidebar-footer">
            <span th:if="${info[build] != null}"><i class="fas fa-tag"></i> <span th:text="${info.build.version}"></span></span>

            <span th:if="${info[git] != null}"><i class="fab fa-github"></i> <span th:text="${info.git.commit.id}"></span></span>
        </div>
    </nav>
</template>

<script th:inline="javascript"  type="application/ecmascript">
    let sidebar_collapsed = [[${sidebar_collapsed}]];

    new Vue({
        el: "#sidebar",
        template: "#sidebar-template",
    });

    $(document).ready(function () {
        /*-- sidebar js --*/
        // sidebarCollapse button is in another part (header)
        $('#sidebarCollapse').on('click', function () {
            sidebar_collapsed = ! sidebar_collapsed;
            document.cookie = `sidebar_collapsed=${sidebar_collapsed}`;
            $('#sidebar').toggleClass('active');
        });
    });
</script>