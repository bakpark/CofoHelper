package com.projects.cofohelper.service.problem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
import com.projects.cofohelper.domain.problem.JsoupCrawler;
import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.domain.problem.ProblemRepository;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.exception.InvalidParameterException;
import com.projects.cofohelper.exception.alreadyexist.ProblemAlreadyExistException;
import com.projects.cofohelper.exception.notfound.ProblemNotFoundException;

@Service
public class ProblemService {

	@Autowired
	ProblemRepository problemRepo;

	@Autowired
	HtmlAttributeRepository attrRepo;

	@Autowired
	HtmlNodeRepository nodeRepo;

	public Problem register(ProblemRegisterDto requestDto) throws Exception {
		String name = requestDto.getProblemName();
		if (problemRepo.findByName(name) != null)
			throw new ProblemAlreadyExistException("Problem already exist <" + name + ">");
		Problem problem = new Problem(name);
		setContent(problem);
		problemRepo.save(problem);
		return problem;
	}

	public String getHtml(String problemName) {
		Problem problem = problemRepo.findByName(problemName);
		if (problem == null)
			throw new ProblemNotFoundException("Problem not Found: " + problemName);
		return problem.getHtmlContent().writeHtml();
	}

	public boolean isExist(String problemName) {
		Problem problem = problemRepo.findByName(problemName);
		return problem != null;
	}

	private void setContent(Problem problem) throws Exception {

		JsoupCrawler.setSSL();
		String url = getCodeforceUrl(problem.getName());
		Document doc = Jsoup.connect(url).get();
		Element element = null;
		try {
			element = doc.select("div.problemindexholder").get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidParameterException("no such problem in codeforce:" + problem.getName());
		}
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
		return "https://codeforces.com/problemset/problem/" + infos[0] + "/" + infos[1];
	}

	private void convertElementToCustom(Element element, HtmlNode parentNode) {
		HtmlNode htmlNode = new HtmlNode(element.tagName(), parentNode);
		nodeRepo.save(htmlNode);
		parentNode.insertChild(htmlNode);
		Attributes attributes = element.attributes();
		Iterator<Attribute> it = attributes.iterator();
		while (it.hasNext()) {
			Attribute attr = it.next();
			HtmlAttribute htmlAttr = HtmlAttribute.builder().htmlNode(htmlNode).name(attr.getKey())
					.value(attr.getValue()).build();
			attrRepo.save(htmlAttr);
			htmlAttr.setHtmlNode(htmlNode);
			htmlNode.insertAttributes(htmlAttr);
		}
//		System.out.print("<"+htmlNode.getTag());
//		for(HtmlAttribute attr : htmlNode.getAttributes()) {
//			System.out.print(" "+attr.getName()+":"+attr.getValue());
//		}
//		System.out.println(">");
//		System.out.println(element.ownText());
		htmlNode.setInnerText(element.ownText());
		for (int i = 0; i < element.childrenSize(); i++) {
			Element child = element.child(i);
			convertElementToCustom(child, htmlNode);
		}
	}

}
