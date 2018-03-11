<?php $minimumDonation = 99; ?>
<html>
<head>
<title>Donate - ProbabilityManagement.Org</title>
<base href="http://probabilitymanagement.org/" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="BasicVerdanaCSS.css" rel="stylesheet" type="text/css">
<link href="prob.css" rel="stylesheet" type="text/css">


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
				
			    <div width="50%" style="text-align: center;"><a href="index.html"><b>Click here to return to Probabilitymanagement.org</b></a>
			    </div>
			</td>
			<td width="35%" valign="top" style="background-color: #e4e4e4; padding: 20px 15px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-weight: normal;">
				<div style="font-size: 16px; line-height: 1.5; color: #616265;">
					<p>Probability Management is a decision support framework for dealing with uncertainties much in the way that traditional data management deals with numbers.</p>

					<p>We promote a coherent, collaborative and intuitive approach to curing the Flaw of Averages by representing uncertainties as arrays of thousands of potential outcomes, rather than single best guess numbers.</p>

					<p style="margin-bottom: 0;">A donation of $99 or more gives you access to our Member Area. Thank you for supporting our efforts!</p>
				</div>
				<div style="border: 2px solid #333; margin-top: 20px; margin-bottom: 20px; padding: 10px;">
					<p style="margin: 0;">Probability Management, Inc. has applied for recognition under Internal Revenue Code section 501(c)(3) for non-profit consideration, and donations are currently fully tax deductible. Should the organization be denied public charity status, the deduction as a qualified donation will be lost and the contribution will have to be considered a business expense if eligible. All donations  will be used solely for the purpose of promoting and developing Probability Management. Our tax ID # is 46-0572110. </p>
				</div>
			</td>
		</tr>
	</table>

</td>
</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="2" bgcolor="#CCCCCC">
  <tr> 
    <td class='hidelinkbot' align="center">
	<a href="index.html">Home</a> &#149;
      <a href="news-publication.html">News &amp; Publications</a> &#149; 
      <a href="models.html">Models</a> &#149; 
      <a href="sip-math.html">SipMath</a> &#149; 
      <a href="standards.html">Standards</a> &#149; 
      <a href="about-us.html">About Us</a> &#149; 
      <a href="secure/members.htm">Member Area</a> 
      <script type="text/javascript" language="javascript">
<!--
// Email obfuscator script 2.1 by Tim Williams, University of Arizona
// Random encryption key feature by Andrew Moulden, Site Engineering Ltd
// This code is freeware provided these four comment lines remain intact
// A wizard to generate this code is at http://www.jottings.com/obfuscator/

//-->
</script><noscript>Sorry, you need Javascript on to email us.</noscript></td>
  </tr>
    <tr><td align="center"> &copy;2014 ProbabilityManagement.org<br /><br /><br /></td></tr>
</table>

</td>
<TD BGCOLOR="#6699CC" width="1"><IMG SRC="Library/pixel_6699CC.gif" ALT="" BORDER="0" width="1" height="1"></TD>
</tr></TABLE>
  </center>
</div>

</body>
</html>

