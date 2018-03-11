<?php $minimumDonation = 99; ?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="ProbabilityManagement.org promotes the communication and calculation of uncertainty through education, best practices, and the open SIPmath standard.">
	<meta name="author" content="Sam Savage, Thomas von der Ohe">

	<title>Thank You for Supporting ProbabilityManagement.org</title>

	<!-- Bootstrap core CSS -->
	<link href="css/bootstrap.css" rel="stylesheet">


	<!-- Add custom CSS here -->
	<link href="css/modern-business.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="css/box.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
   <div> Warning: Parts of this website will not be displayed properly, as you are using an old version of Internet Explorer (Version 8 or lower). If you want to upgrade, you can go <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">here.</a> </div>
<![endif]-->   

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<!-- You'll want to use a responsive image option so this logo looks good on devices - I recommend using something like retina.js (do a quick Google search for it and you'll find it) -->
				<a class="navbar-brand" href="index.html"><img src="img/pmlogonav.png" /></a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav navbar-right">
					<!-- <li><a href="index.html" ><span>Home</span></a></li> -->
					<li><a href="what-we-do.html"><span>What We Do</span></a></li>
					<li><a href="standards.html"><span>SIPmath Standard</span></a></li>
					<li><a href="tools.html"><span>SIPmath Tools</span></a></li>
					<li><a href="models.html"><span>Models</span></a></li>
					<li><a href="news-publication.html"><span>News &amp; Publications</span></a></li>
					<li><a href="about-us.html"><span>About Us</span></a></li>
				</ul>
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container -->
	</nav>


	<div class="container"> <!-- thankyou return content-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:-20">
  <tr>

<td width="75%" >
	<table width="100%" border=0 cellpadding=0 cellspacing=0>
		<tr>
			<td width="65%" valign="top">
				<div style="padding: 20px;">
					<div style="text-align: center;">
						<p style="font-size: 1.2em;"><b>Thank you for your donation!</b></p>
						
						<?php if( $_REQUEST['amt'] >= $minimumDonation ): ?>
							<div style="margin: 30px auto; padding: 15px; text-align: center; width: 80%; border-top: 1px solid #ccc; border-bottom: 1px solid #ccc;">
								Your donation qualified for Membership access! Please check your email in the next 5-10 minutes for your username and password information.
							</div>
						<?php elseif( isset( $_REQUEST['amt'] ) ): ?>
							<div style="margin: 30px auto; padding: 15px; text-align: center; width: 80%; border-top: 1px solid #ccc; border-bottom: 1px solid #ccc;">
								Your support is essential in helping us continue our efforts.
							</div>
						<?php else: ?>
							<div style="margin: 30px auto; padding: 15px; text-align: center; width: 80%; border-top: 1px solid #ccc; border-bottom: 1px solid #ccc;">
								If your donation qualified for Membership access, please check your email in the next 5-10 minutes for your username and password information.
							</div>
						<?php endif; ?>
						
						<p style="font-size: 0.9em;"><i>Your transaction has been completed, and a receipt for your purchase has been emailed to you. You may log into your account at <a href="http://www.paypal.com/us">www.paypal.com/us</a> to view details of this transaction.</i></p>
					</div>
				
			    
			</td>
			<td width="35%" valign="top" style="background-color: #e4e4e4; padding: 20px 15px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-weight: normal;">
				<div style="font-size: 16px; line-height: 1.5; color: #616265;">
					<p>Probability Management is a decision support framework for dealing with uncertainties much in the way that traditional data management deals with numbers.</p>

					<p>We promote a coherent, collaborative and intuitive approach to curing the Flaw of Averages by representing uncertainties as arrays of thousands of potential outcomes, rather than single best guess numbers.</p>

					<p style="margin-bottom: 0;">A donation of $99 or more gives you access to our Member Area. Thank you for supporting our efforts!</p>
				</div>
				<div style="border: 2px solid #333; margin-top: 20px; margin-bottom: 20px; padding: 10px;">
					<p style="margin: 0;">Probability Management, Inc. is a non-profit organization with 501(c)(3) tax-exempt status in the United States. Donations are tax-deductible to the extent allowed by law. All donations will be used solely for the purpose of promoting and developing Probability Management. Our tax ID # is 46-0572110.</p>
				</div>
			</td>
		</tr>
	</table>

</td>
</tr>
</table>

	</div> <!-- /thankyou -->
	 <div class="container">
          <div class="row">
            <div class="col-lg-12">
              <hr>
              <p>Copyright &copy; ProbabilityManagement.org 2014 <b>To contact us, email</b> Melissa@ProbabilityManagement.org</p>
            </div>
          </div>

      </div><!-- /.container -->

	<!-- Bootstrap core JavaScript -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/modern-business.js"></script>
</body>
</html>

