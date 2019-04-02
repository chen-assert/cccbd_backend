package mail;


import com.sendgrid.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Random;

import static sql.sqldata.sendgrid_api_key;


@Path("/verify_code")
@Produces("text/plain; charset=UTF-8")
public class Mail {
    String content3 = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
            "<html>\n" +
            "\t<head>\n" +
            "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
            "\t<html>\n" +
            "\t<head>\n" +
            "\t\t<meta content=\"text/html;charset=UTF-8\" http-equiv=\"Content-Type\">\n" +
            "\t\t<style media=\"all\" type=\"text/css\">\n" +
            "\t\ttd, p, h1, h3, a {font-family: Helvetica, Arial, sans-serif;}\n" +
            "\t\t</style>\n" +
            "\t</head>\n" +
            "\t\n" +
            "<body LINK=\"#c6d4df\" ALINK=\"#c6d4df\" VLINK=\"#c6d4df\" TEXT=\"#c6d4df\" style=\"font-family: Helvetica, Arial, sans-serif; font-size: 14px; color: #c6d4df;\" >\n" +
            "<table style=\"width: 538px; background-color: #393836;\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">\n" +
            "\t<tr>\n" +
            "\t\t<td style=\" height: 65px; background-color: #000000; border-bottom: 1px solid #4d4b48;\">\n" +
            "              <img src=\"https://store.steampowered.com/public/shared/images/email/email_header_logo.png\" width=\"538\" height=\"65\" alt=\"Steam\">\n" +
            "        </td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td bgcolor=\"#17212e\">\n" +
            "\t\t\t<table width=\"470\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding-left: 5px; padding-right: 5px; padding-bottom: 10px;\">\n" +
            "\n" +
            "\t\t\t\t<tr bgcolor=\"#17212e\">\n" +
            "\t\t\t\t\t<td style=\"padding-top: 32px;\">\n" +
            "\t\t\t\t\t<span style=\"padding-top: 16px; padding-bottom: 16px; font-size: 24px; color: #66c0f4; font-family: Arial, Helvetica, sans-serif; font-weight: bold;\">\n" +
            "\t\t\t\t\t\t敬爱的 accountname：\n" +
            "\t\t\t\t\t</span><br>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<tr>\n" +
            "\t\t\t\t\t<td style=\"padding-top: 12px;\">\n" +
            "\t\t\t\t\t<span style=\"font-size: 17px; color: #c6d4df; font-family: Arial, Helvetica, sans-serif; font-weight: bold;\">\n" +
            "\t\t\t\t\t\t<p>以下是您登录帐户 accountname 时所需的验证码：</p>\n" +
            "\t\t\t\t\t</span>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\n" +
            "\t\t\t\t<tr>\n" +
            "\t\t\t\t\t<td>\n" +
            "\t\t\t\t\t\t<div>\n" +
            "\t\t\t\t\t\t\t<span style=\"font-size: 24px; color: #66c0f4; font-family: Arial, Helvetica, sans-serif; font-weight: bold;\"> verify_code </span>\n" +
            "\t\t\t\t\t\t</div>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "                <!--\n" +
            "\t\t\t\t<tr bgcolor=\"#121a25\">\n" +
            "\t\t\t\t\t<td style=\"padding: 20px; font-size: 12px; line-height: 17px; color: #c6d4df; font-family: Arial, Helvetica, sans-serif;\">\n" +
            "\t\t\t\t\t\t\t<p style=\"padding-bottom: 10px; color: #c6d4df;\">这封电子邮件生成是由于地址为 <a style=\"color: #c6d4df;\" href=\"https://steamcommunity.com/actions/ReportSuspiciousLogin?stoken=02249566bd41b95f45613e7e19aeda2d79048de998672653a7399aa97143ffa924f284b88c38ee271d5f12f18fe6c549\">60.206.255.42（CN）</a>的电脑试图登录您的帐户。该地址在试图登录时输入了您正确的帐户名称和密码。</p>\n" +
            "\t\t\t\t\t\t\t<p style=\"padding-bottom: 10px; color: #c6d4df;\">要完成登录，您将需要 Steam 令牌验证码。<span style=\"color: #ffffff; font-weight: bold;\">无人可以在不访问这封电子邮件的前提下访问您的帐户。</span></p>\n" +
            "\t\t\t\t\t\t\t<p style=\"padding-bottom: 10px; color: #c6d4df;\"><span style=\"color: #ffffff; font-weight: bold;\">如果您未曾试图登录</span>，请更改您的 Steam 密码，并考虑更改您的电子邮件密码，确保您的帐户安全。</p>\n" +
            "\t\t\t\t\t\t\t<p style=\"padding-top: 10px; color: #61696d;\">如果您无法访问您的帐户，那么可以<a style=\"color: #8f98a0;\" href=\"https://help.steampowered.com/zh-cn/wizard/HelpUnauthorizedLogin?stoken=wGdLv3v3epBPZ%2BHHaNH0kjlbpFFtSNk9EHQzc1r0q5GF18EMjPAtumC4TLphd0GQb%2FAaaWUeSBaOAHlsxytLtQ%3D%3D\">使用该帐户专用救援链接</a>以获得救援或自行锁定您的帐户的协助。</p>\n" +
            "\t\t\t\t\t</td>\n" +
            "\t\t\t\t</tr>\n" +
            "\t\t\t\t-->\n" +
            "\n" +
            "\n" +
            "\t\t\t\t<tr>\n" +
            "\t\t\t\t\t<td style=\"font-size: 12px; color: #6d7880; padding-top: 16px; padding-bottom: 60px;\">\n" +
            "                    \t\t\tTeam CCCBD<br>\n" +
            "                    \t\t\t<a style=\"color: #8f98a0;\" href=\"https://help.cccbd.top\">https://help.cccbd.top</a><br>\n" +
            "                    </td>\n" +
            "\t\t\t\t</tr>\n" +
            "\n" +
            "\t\t\t</table>\n" +
            "\t\t</td>\n" +
            "\t</tr>\n" +
            "\n" +
            " <td bgcolor=\"#000000\">\n" +
            "     \t<table width=\"460\" height=\"55\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "       \t <tr valign=\"top\">\n" +
            "          <td width=\"110\">\n" +
            "            <a href=\"http://www.valvesoftware.com/\" target=\"_blank\" style=\"color: #8B8B8B; font-size: 10px; font-family: Trebuchet MS, Verdana, Arial, Helvetica, sans-serif; text-transform: uppercase;><span style=\"font-size: 10px; color: #8B8B8B; font-family: Trebuchet MS,Verdana,Arial,Helvetica,sans-serif; text-transform: uppercase\">\n" +
            "\t\t\t<img src=\"http://storefront.steampowered.com/v/img/gift/VALVe.gif\" alt=\"VALVE\" width=\"92\" height=\"26\" hspace=\"0\" vspace=\"0\" border=\"0\" align=\"top\"></span></a></td>\n" +
            "\t\t  <td width=\"350\" valign=\"top\">\n" +
            "\t\t   <span style=\"color: #999999; font-size: 9px; font-family: Verdana, Arial, Helvetica, sans-serif;\">© Hibernia-Sino Travel Insurance , Inc. 保留所有权利。所有商标均为其在爱尔兰及其它国家/地区的各自持有者所有。</span>\n" +
            "\t\t  </td>\n" +
            "       \t </tr>\n" +
            "        </table>\n" +
            "\t</td>\n" +
            "  </tr>\n" +
            "</table>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    @GET
    @Path("/")
    public Response sendmail(@QueryParam("address") String address) throws SQLException {
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            Email from = new Email("admin@cccbd.top");
            String subject = "Verification code from Hibernia-Sino Travel";
            Email to = new Email(address);
            int flag = new Random().nextInt(899999);
            flag += 100000;
            content3 = content3.replace("accountname", address);
            content3 = content3.replace("verify_code", String.valueOf(flag));
            Content content = new Content("text/html", content3);
            com.sendgrid.Mail mail = new com.sendgrid.Mail(from, subject, to, content);
            //String sendgrid_api_key = System.getenv("SENDGRID_API_KEY");
            SendGrid sg = new SendGrid(sendgrid_api_key);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            com.sendgrid.Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return Response.status(200).entity("send success, to:" + address).build();
        } catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }

    @POST
    public Response sendmail2(@FormParam("address") String address)
            throws SQLException, ClassNotFoundException {
        return sendmail(address);
    }
}