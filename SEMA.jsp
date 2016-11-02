//CHR-CHN
<%--
 * 
 * @author Mohammad Javed
 * SEMA
 *
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<jsp:include page="fragments/header.jsp" />
<body>
<div class="container">
  <div class="row">
    <div class="col-md-6 col-xs-10 col-xs-offset-1 col-md-offset-3 jumbotron">

        <form action="supplier" method="post"">
		<fieldset>
		  <div class="text-left ">
				 <div class="input-group col-xs-12 col-md-12">   
				 <div><label for="Exhibitor">Exhibitor Name</label></div>
		         <input type="text" class="form-control" name=exhibitor value="${Exhibitor.exhibitor_company_name}" readonly >
		         <input type="hidden" class="form-control" name=exhibitor_id value="${Exhibitor.exhibitor_id}" readonly >
		        </div>
		        <br>
		        <div><label for="Matched Supplier">Matched Supplier</label></div>
	             <div class="input-group col-xs-8 col-md-6" > 
		               <select name="matchedSupplier"  id="matchedSupplier" class=" js-example-placeholder-single form-control">
                         <c:forEach var="items" items="${matchedSupplierList}">
					     <option value="${items}"> ${items.value}</option>
					    </c:forEach>
			 </select>
					
			    </div>
     		<br>
              <div><label for="Month">Month</label></div>
				<div class="input-group input-group" id="select-css"> 
		
				    <select class="form-control " id="month" name="month">
				        <option value="1:January">January</option>
				        <option value="2:February">February</option>
				        <option value="3:March">March</option>
				        <option value="4:April">April</option>
				        <option value="5:May">May</option>
				        <option value="6:June">June</option>
				        <option value="7:July">July</option>
				        <option value="8:August">August</option>
				        <option value="9:September">September</option>
				        <option value="10:October">October</option>
				        <option value="11:November">November</option>
				        <option value="12:December">December</option>
				    </select>
				</div>
		<br>
				<div><label for="Potential Dollars">Potential Dollars </label> (USD)</div>
				<div  id="info"></div>
					
				<div class="input-group col-xs-6 col-md-4 "> 
					<span class="input-group-addon">$</span>
					<input type="number" min="0" max="9999999"  class="form-control" id="potential" name="potential" value="" required  >

				</div>
             <br>  
				 <div><label for="Actual Dollars ">Actual Dollars </label> (USD)</div>
				   <div  id="info1"></div>
				
				 <div class="input-group col-xs-6 col-md-4 ">
			      <span class="input-group-addon">$</span>
					<input type="number" min="0" max="9999999"  class="form-control" id="actual" name="actual"  value=""  required >
<!--step="0.01"-->
                 </div>
			<br>
                 <div class=" col-xs-6 col-md-4  "> 
			        <input type="submit" name="home"  class="form-control submit-flat-button" value="Save"  >
			     </div>
			      <div class="col-xs-6 col-md-4 col-md-offset-1 "> 
			        <input type="button" value="Cancel" class="form-control submit-flat-button btn"  onclick="history.back(-1)" />
				  </div>
	        </div>
		</fieldset>
	 </form>
   </div>
  </div>
</div>
  
<jsp:include page="fragments/footer.jsp" />
  <script type="text/javascript">
   
   $(document).ready(function() {
   $(".js-example-placeholder-single").select2({
  placeholder: "Select your Matches",
  allowClear: true });

   var d = new Date();
   n = d.getMonth();
 // console.log("Current month"+n);
   $('#month option:eq('+n+')').prop('selected', true);
   });
  
   $( "form" ).submit(function( event ) {
      var len=$( "input#potential" ).val().length;
      var len1=$( "input#actual").val().length;
      var num=$( "input#potential" ).val();
      var num1=$( "input#actual" ).val();
      
           if ( len ===0 || len >7||!$.isNumeric(num)){
     $("div#info").css({
     "float":"right",
    "border": "1px solid red",
    "background": "#FFCECE",
    "padding":"8px",
    "border-radius":"5px"
    });
     event.preventDefault();
     }
       if ( len1 ===0 || len1 >7||!$.isNumeric(num1)){
     $("div#info1").css({
    "float":"right",
    "border": "1px solid red",
    "background": "#FFCECE",
    "padding":"8px",
    "border-radius":"5px"
    
    });
     event.preventDefault();
    }
    if (len===0)
    {
    $( "div#info").text("Please enter a value").show().fadeOut( 4000 );
    }
    if (len>7)
    {
     $( "div#info").text("Please check the value length").show().fadeOut( 4000 );
     }
    if (len1===0)
    {
    $( "div#info1").text("Please enter a value").show().fadeOut( 4000 );
    }
    if (len1>7)
    {
     $( "div#info1").text("Please check the value length").show().fadeOut( 4000 );
     }
  if (!$.isNumeric(num)){
  $( "div#info").text("Please enter a number").show().fadeOut( 4000 );
 
  }
   if (!$.isNumeric(num1)){
  $( "div#info1").text("Please enter a number").show().fadeOut( 4000 );

  }
});
</script>
</body>
</html>

