package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class ModifyController
 */
@WebServlet("/notice/modify.do")
public class ModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//기존 공지사항 내용을 가져오귀~
		// SELECT * FROM NOTICE_TBL WHERE NOTICE_NO = ?
		NoticeService service = new NoticeService();
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		Notice notice = service.selectOneByNo(noticeNo);
		request.setAttribute("notice", notice);
		
		request.getRequestDispatcher("/WEB-INF/views/notice/modify.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int noticeNo =  Integer.parseInt(request.getParameter("noticeNo")) ;
		String noticeSubject = request.getParameter("noticeSubject");
		String noticeContent = request.getParameter("noticeContent");
		Notice notice = new Notice(noticeNo,noticeSubject,noticeContent);
		
		
		// UPDATE NOTICE_TBL SET NOTICE_SUBJECT=  ? , NOTICE_CONTENT = ? WHERE NOTICE_NO = ?
		NoticeService service = new NoticeService();
		int result = service.updateNotice(notice);
		
		if ( result > 0 ) {
			
			//성공
			response.sendRedirect("/notice/list.do?currentPage=1");
			
			
		}else { 
			//실패
			request.setAttribute("msg", "수정이 완료 되지 않았습니다.");
			RequestDispatcher view = request.getRequestDispatcher("/WEB/views/common/serviceFailed.jsp");
			view.forward(request, response);
		}
		
		
		
		
		
	}

}
