<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- mobile metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">

    <!-- site metas -->
    <title>Gaia</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- bootstrap css -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css" />
    <!-- site css -->
    <link rel="stylesheet" href="/css/style.css" />
    <!-- responsive css -->
    <link rel="stylesheet" href="/css/responsive.css" />
    <link rel="stylesheet" href="/css/color_2.css" />

    <link rel="stylesheet" href="/webjars/font-awesome/5.8.2/css/all.css" />

</head>
<body class="dashboard dashboard_2">
<div class="full_container">
    <div class="inner_container">

        <div id="sidebar-placeholder"></div>

        <!-- right content -->
        <div id="content">

            <div th:replace="~{layout/topbar}"></div>

            <div class="container-fluid">
                <div class="row column_title">
                    <div class="col-md-12">
                        <div class="page_title">
                            <h2>  </h2>
                        </div>
                    </div>
                </div>
                <div class="row column1">


                </div>
            </div>

        </div>
    </div>
</div>
<!-- jQuery & bootstrap-->
<script src="/webjars/jquery/3.0.0/jquery.min.js"></script>
<script src="/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<div th:replace="vue_templates/sidebar"></div>

</body>
</html>