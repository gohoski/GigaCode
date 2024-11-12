package pw.appdata.gigacode;

import jakarta.servlet.jsp.tagext.*;
import jakarta.servlet.jsp.*;
import org.json.JSONArray;

import java.io.*;

public class ExerciseTag extends SimpleTagSupport {
    public int id;
    public String type;
    public JSONArray data;
    public String test;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<div class=\"columns\">\n" +
                "            <c:forEach var=\"tile\" items='${data.iterator()}' varStatus=\"loop\">\n" +
                "                ${!loop.first ? '<div class=\"inherited\"></div>' : ''}\n" +
                "                <div class=\"column\">\n" +
                "                    <article class=\"box notification\">\n" +
                "                        <p class=\"title\">${tile.get(\"name\")}</p>\n" +
                "                        <div class=\"content\">\n" +
                "                            <c:forEach var=\"var\" items='${tile.get(\"variables\").toList()}'>\n" +
                "                                <div>\n" +
                "                                    <span class=\"modifier <c:out value='${var.get(\"modifier\")}'/>\"></span>\n" +
                "                                    <c:out value='${var.get(\"name\")}'/>:\n" +
                "                                    <i><c:out value='${var.get(\"type\")}'/></i>\n" +
                "                                </div>\n" +
                "                            </c:forEach>\n" +
                "                            <hr/>\n" +
                "                            <c:forEach var=\"func\" items='${tile.get(\"functions\").toList()}'>\n" +
                "                                <div>\n" +
                "                                    <span class=\"modifier <c:out value='${func.get(\"modifier\")}'/>\"></span>\n" +
                "                                    <c:out value='${func.get(\"name\")}'/> (<c:forEach var=\"var\" items=\"${func.get('variables')}\" varStatus=\"loop\"><c:out value=\"${var.get('name')}\" />:\n" +
                "                                        <i><c:out value=\"${var.get('type')}\" /></i><%--\n" +
                "                                        --%>${!loop.last ? ', ' : ''}</c:forEach>)<%--\n" +
                "                                --%></div>\n" +
                "                            </c:forEach>\n" +
                "                        </div>\n" +
                "                    </article>\n" +
                "                </div>\n" +
                "            </c:forEach>\n" +
                "        </div>");
    }
}