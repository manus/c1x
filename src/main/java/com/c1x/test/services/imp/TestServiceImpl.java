package com.c1x.test.services.imp;

import com.c1x.test.services.AbstractServiceImpl;
import com.c1x.test.services.TestService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;

/**
 * Created by manu on 05/02/15.
 */
@Path("/service")
public class TestServiceImpl extends AbstractServiceImpl implements TestService
{
    private static Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    //private static final String pushBackUrl = "http://clickmeter.com/conversion.aspx?";
    //private static final String paramUrl = "&id=15728BAC22AE4AFFBD9A363E348A2E50&val=1&com=1";

    private static final String pushBackUrl = "http://conversions.clickmeter.com/s2s?";
    private static final String paramUrl = "&id=15728BAC22AE4AFFBD9A363E348A2E50&val=1&com=1";

    private static final String conversionCode = "<!--ClickMeter.com code for conversion: Test_Conversion -->\n" +
            "<script type='text/javascript'>\n" +
            "    var ClickMeter_conversion_id = '15728BAC22AE4AFFBD9A363E348A2E50';\n" +
            "    var ClickMeter_conversion_value = '0.00';\n" +
            "    var ClickMeter_conversion_commission = '0.00';\n" +
            "    var ClickMeter_conversion_parameter = 'empty';\n" +
            "</script>\n" +
            "<script type='text/javascript' id='cmconvscript' src='//s3.amazonaws.com/scripts-clickmeter-com/js/conversion.js'></script>\n" +
            "<noscript>\n" +
            "<img height='0' width='0' alt='' src='//www.clickmeter.com/conversion.aspx?id=15728BAC22AE4AFFBD9A363E348A2E50&val=0.00&param=empty&com=0.00' />\n" +
            "</noscript>";

    @Path("/pushBack")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String test(@QueryParam("s1") String param) throws Exception
    {
        logger.info("Received param = " + param);

        String url = pushBackUrl + "clickId=" + URLEncoder.encode(param) + paramUrl;

        HttpClient client = new DefaultHttpClient();
        int resCode = 0;
        HttpGet get = new HttpGet(url);

        try{
            HttpResponse response = client.execute(get);
            resCode = response.getStatusLine().getStatusCode();
            logger.debug("Response code of subscription"+resCode);
        }
        catch (Exception e){

            logger.error("Error in subscription - "+e.getMessage(), e);
        }
        /*String html = "<html><head><title>This is a test</title></head><body>";
        html += conversionCode;
        html += "</body></html>";
        return html;*/
        return "{\"stat\":\"ok\"}";
    }
}
