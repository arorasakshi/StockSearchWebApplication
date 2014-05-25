<?php ?>
<?php
header('Content-type: text/xml');
echo "<?xml version='1.0' encoding='UTF-8'?>";



	if(isset($_GET['company']))
     		$company = urlencode($_GET['company']); 
	
$yahooRSSFeedUrl ="http://feeds.finance.yahoo.com/rss/2.0/headline?s=${company}&region=US&lang=en-US"; 
$yahooStockDataUrl = "http://query.yahooapis.com/v1/public/yql?q=Select%20Name%2C%20Symbol%2C%20LastTradePriceOnly%2C%20Change%2C%20ChangeinPercent%2C%20PreviousClose%2C%20DaysLow%2C%20DaysHigh%2C%20Open%2C%20YearLow%2C%20YearHigh%2C%20Bid%2C%20Ask%2C%20AverageDailyVolume%2C%20OneyrTargetPrice%2C%20MarketCapitalization%2C%20Volume%2C%20Open%2C%20YearLow%20from%20yahoo.finance.quotes%20where%20symbol%3D%22${company}%22&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
$yahooChart = "http://chart.finance.yahoo.com/t?s=${company}&amp;lang=en-US&amp;amp;width=300&amp;height=180";
$chart =  htmlspecialchars( $yahooChart   , ENT_QUOTES, 'UTF-8');
		
		//reading xml for RSS lower part
		
			$RSSxml=simplexml_load_file($yahooRSSFeedUrl);
			$stocKxml=simplexml_load_file($yahooStockDataUrl);
			$fstStockchild = $stocKxml->children();
			$secondStockchild = $fstStockchild->children();					
			$fstRSSchild = $RSSxml->children();
		
	

function is_num($val)
{
    if($val == "" || is_null($val) == true)
	return $val;	
	else
	return	number_format(round($val));
	
}
function is_decimal($val){
if (strpos($val,'.') !== false)
{      $realVal = explode(".",$val)[0];
    	$decimalVal = explode(".",$val)[1];
			$length = strlen($decimalVal);
			if($length>2){
			$newval = substr($decimalVal,0,2);
			$fullvalue = $realVal.".".$newval;
			}
			else{
				$fullvalue = $realVal.".".$decimalVal;
				}
		$fullvalue=number_format($fullvalue,2);
		}
		else{
		if($val == "" || is_null($val) == true)
				$fullvalue=$val;	
		else
			$fullvalue=number_format(round($val));
	}
	return $fullvalue;
}
function generateUpperHalf(SimpleXMLElement $simpleXmlElement)
	{
	
		foreach($simpleXmlElement->children() as $child)
		{
		
			if($child->getName() == "Bid"){$bid = $child;}
			if($child->getName() == "Change"){$Change = $child;}
			if($child->getName() == "DaysLow" ){$dLow = $child;}
			if($child->getName() == "DaysHigh" ){$dHigh = $child;}
			if($child->getName() == "YearLow" ){$YLow = $child;}
			if($child->getName() == "YearHigh" ){$YHigh = $child;}
			if($child->getName() == "MarketCapitalization" ){$marketCap = $child;}
			if($child->getName() == "LastTradePriceOnly" ){$Policy = $child;}			
			if($child->getName() == "Name" ){$Name = $child;}
			if($child->getName() == "Open" ){$open = $child;}	
			if($child->getName() == "ChangeinPercent" ){$changepercent = $child;}
			if($child->getName() == "Symbol"){$Symbol = $child;}			
			if($child->getName() == "OneyrTargetPrice"){$Target = $child;}			
			if($child->getName() == "Volume"){$Volume = $child;}			
			if($child->getName() == "Ask"){$Ask = $child;}			
			if($child->getName() == "AverageDailyVolume"){$AvgVolume = $child;}			
			if($child->getName() == "PreviousClose"){$prev = $child;}
		}
		
		if($marketCap != ""){
		$lastM = substr("$marketCap", -1);  
		$fstM = substr("$marketCap", 0, -1);
		$fstM = is_decimal($fstM);
		$marketCap = $fstM.$lastM;
		}
		
		if($Change>0)
			{
			$ChangeType = "+";
			$change = explode("+",$Change)[1];		
			$imgUrl = "http://www-scf.usc.edu/~csci571/2014Spring/hw6/up_g.gif";
			$imageVal = "<img src='$imgUrl'>";
			$col = "<span id = 'headG' >";			
			}
			else if($Change<0)
			{
			$ChangeType = "-";
			$change = explode("-",$Change)[1];
			$imgUrl = "http://www-scf.usc.edu/~csci571/2014Spring/hw6/down_r.gif";
			$imageVal = "<img src='$imgUrl'>";	
			$col = "<span id = 'headR' >";			}
			else if($Change == 0 || $Change == "0.0" || $Change === "")
			{
			$ChangeType = "";
			$change = $Change;
			$imgUrl = "";
			$imageVal = "";
			$col = "<span id = 'headG' >";			
			}
			else
			{
			$imgUrl = "";
			$imageVal = "";
			$col = "<span id = 'headG' >";
			}
			

			if($changepercent>0)
			{
			$changepercent = explode("+",$changepercent)[1];		
			}
			else if($changepercent<0)
			{
			$changepercent = explode("-",$changepercent)[1];
			}
			else if($changepercent == "")
				$changepercent = $changepercent;
			
			
			echo "<Name>".htmlspecialchars( $Name   , ENT_QUOTES, 'UTF-8')."</Name>\n";
			echo "<symbol>".htmlspecialchars( $Symbol   , ENT_QUOTES, 'UTF-8')."</symbol>\n";
			echo "<Quote>\n";
			echo "<ChangeType>".$ChangeType."</ChangeType>\n";
			echo "<Change>".$change."</Change>\n";
			echo "<ChangeInPercent>".$changepercent."</ChangeInPercent>\n";
			echo "<LastTradePriceOnly>".is_decimal($Policy)."</LastTradePriceOnly>\n";
			echo "<PreviousClose>".is_decimal($prev)."</PreviousClose>\n";
			echo "<DaysLow>".is_decimal($dLow)."</DaysLow>\n";
			echo "<DaysHigh>".is_decimal($dHigh)."</DaysHigh>\n";
			echo "<Open>".is_decimal($open)."</Open>\n";
			echo "<YearLow>".is_decimal($YLow)."</YearLow>\n";
			echo "<YearHigh>".is_decimal($YHigh)."</YearHigh>\n";
			echo "<Bid>".is_decimal($bid)."</Bid>\n";
			echo "<Volume>".is_decimal($Volume)."</Volume>\n";
			echo "<Ask>".is_decimal($Ask)."</Ask>\n";
			echo "<AverageDailyVolume>".is_decimal($AvgVolume)."</AverageDailyVolume>\n";
			echo "<OneYearTargetPrice>".is_decimal($Target)."</OneYearTargetPrice>\n";
			echo "<MarketCapitalization>".$marketCap."</MarketCapitalization>\n";
	
	}
	
	
	
function generateLowerHalf(SimpleXMLElement $simpleXmlElement)
	{
	$flag = false;

	foreach($simpleXmlElement->children() as $child)
	  {
			if($child->getName() == "item")
			{
				foreach($child->children() as $secondchild)
				{

				if($secondchild->getName() == "title")
				{
					$detail = $secondchild;
					if(strcmp($detail,"Yahoo! Finance: RSS feed not found") == 0)
						$flag = true;
				
				//$detail=stripslashes($detail);

				}
				if($secondchild->getName() == "link")
				$url = $secondchild;
				}
				$url =  htmlspecialchars( $url   , ENT_QUOTES, 'UTF-8');
				$detail =  htmlspecialchars( $detail   , ENT_QUOTES, 'UTF-8');


				echo "<Item>\n";
				echo "<Title>".$detail."</Title>\n";
				echo "<Link>".$url."</Link>\n";
				echo "</Item>\n";
								
			}
	  }	
	}

	echo "<result>\n";
	generateUpperHalf($secondStockchild);
	echo "</Quote>\n";
	echo "<News>\n";
	generateLowerHalf($fstRSSchild);
	echo "</News>\n";
	echo "<StockChartImageURL>\n";
	echo $chart ;
	echo "</StockChartImageURL>\n";
	echo "</result>\n";
?>