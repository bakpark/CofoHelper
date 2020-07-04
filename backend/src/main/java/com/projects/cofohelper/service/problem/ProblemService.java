package com.projects.cofohelper.service.problem;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.domain.problem.HtmlAttribute;
import com.projects.cofohelper.domain.problem.HtmlAttributeRepository;
import com.projects.cofohelper.domain.problem.HtmlNode;
import com.projects.cofohelper.domain.problem.HtmlNodeRepository;
import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.domain.problem.ProblemRepository;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;

@Service
public class ProblemService {

	@Autowired
	ProblemRepository problemRepo;
	
	@Autowired
	HtmlAttributeRepository attrRepo;
	
	@Autowired
	HtmlNodeRepository nodeRepo;
	
	public Problem register(ProblemRegisterDto requestDto) {
		String name = requestDto.getProblemName();
		Problem problem = new Problem(name);
		try {
			setContent(problem);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		problemRepo.save(problem);
		return problem;
	}

	private void setContent(Problem problem) throws IOException, URISyntaxException {

		String url = getCodeforceUrl(problem.getName());
		Document doc = Jsoup.connect(url).get();
		
//		URL path = ClassLoader.getSystemResource("1353-A.html");
//		File input = new File(path.toURI());
//		Document doc = Jsoup.parse(input, "UTF-8");
		Element element = doc.select("div.problemindexholder").get(0);
		HtmlNode html = new HtmlNode("html", null);
		HtmlNode body = new HtmlNode("body", html);
		nodeRepo.save(html);
		nodeRepo.save(body);
		html.insertChild(body);
		convertElementToCustom(element, body);
		problem.setHtmlContent(html);
	}

	
	private String getCodeforceUrl(String problemName) {
		String[] infos = problemName.split("-");
		return "https://codeforces.com/problemset/problem/" + infos[0]+"/"+infos[1];
	}
	private void convertElementToCustom(Element element, HtmlNode parentNode) {
		HtmlNode htmlNode = new HtmlNode(element.tagName(), parentNode);
		nodeRepo.save(htmlNode);
		parentNode.insertChild(htmlNode);
		Attributes attributes = element.attributes();
		Iterator<Attribute> it = attributes.iterator();
		while(it.hasNext()) {
			Attribute attr = it.next();
			HtmlAttribute htmlAttr = HtmlAttribute.builder()
										.htmlNode(htmlNode)
										.name(attr.getKey())
										.value(attr.getValue())
										.build();
			attrRepo.save(htmlAttr);
			htmlNode.insertAttributes(htmlAttr);
		}
		System.out.print("<"+htmlNode.getTag());
		for(HtmlAttribute attr : htmlNode.getAttributes()) {
			System.out.print(" "+attr.getName()+":"+attr.getValue());
		}
		System.out.println(">");
		htmlNode.setInnerText(element.ownText());
		System.out.println(element.ownText());
		for(int i=0;i<element.childrenSize();i++) {
			Element child = element.child(i);
			convertElementToCustom(child, htmlNode);
		}
	}

}
