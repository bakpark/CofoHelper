package com.projects.cofohelper.service.problem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.cofohelper.DefaultRunScript;
import com.projects.cofohelper.domain.html.HtmlAttributeRepository;
import com.projects.cofohelper.domain.html.HtmlNodeRepository;
import com.projects.cofohelper.domain.problem.JsoupCrawler;
import com.projects.cofohelper.domain.problem.Problem;
import com.projects.cofohelper.domain.problem.ProblemRepository;
import com.projects.cofohelper.dto.request.ProblemRegisterDto;
import com.projects.cofohelper.exception.InvalidParameterException;
import com.projects.cofohelper.exception.alreadyexist.ProblemAlreadyExistException;
import com.projects.cofohelper.exception.notfound.ProblemNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public String getHtml(Long problemId) {
		Problem problem = problemRepo.getOne(problemId);
		return problem.getHtmlStr();
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
		
		problem.setHtmlStr(element.html());


	}
	

	private String getCodeforceUrl(String problemName) {
		String[] infos = problemName.split("-");
		return "https://codeforces.com/problemset/problem/" + infos[0] + "/" + infos[1];
	}



	public Problem getProblem(Long problemId) {
		Problem problem = problemRepo.getOne(problemId);
		if(problem == null)
			throw new ProblemNotFoundException("Problem not Found problemId: " + problemId);
		return problem;
	}

}


/********************************************* not used!
public String getHtml(Long problemId) {
	Problem problem = problemRepo.getOne(problemId);
	if (problem == null)
		throw new ProblemNotFoundException("Problem not Found problemId: " + problemId);
	return problem.getHtmlContent().writeHtml();
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
	htmlNode.setInnerText(element.ownText());
	for (int i = 0; i < element.childrenSize(); i++) {
		Element child = element.child(i);
		convertElementToCustom(child, htmlNode);
	}
}
************************************************************/