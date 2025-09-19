# Parser

Bài tập Môn Compiler do thầy Võ Đình Hiếu giảng dạy.

BTL01

Cho ngôn ngữ lập trình UPL với các đặc điểm như sau:

- Bắt đầu và kết thúc chương trình bằng cặp từ khóa begin và end

- Có hai kiểu dữ liệu là số nguyên (int) và boolean (bool)

- Id: bắt đầu bằng ký tự chữ, nếu có ký tự số thì phải ở cuối (có thể có nhiều)

- Biến phải được khai báo trước khi sử dụng. Biến có thể được khởi tạo cùng với câu lệnh khai báo (sử dụng phép gán).

- Có 3 phép toán so sánh: lớn hơn (>), lớn hơn hoặc bằng (>=), bằng (==)

- Có phát biểu lựa chọn if then và if then else

- Có phát biểu lặp do ...while

- Có phát biểu for

- Có phát biểu in ra màn hình (print) với tham số là một biểu thức

- Các phép toán cộng (+) và nhân (*) cho số nguyên. * được ưu tiên hơn +. Lập trình viên có thể sử dụng dấu ngoặc () để xác định lại thứ tự tính toán

- Có cơ chế comment như của Java (nghĩa là có cả /*...*/ và //...)

Example:

	begin
	
	 	int x;
		int y = x + 1;
		/*	comments
		cho nhiều dòng
		*/
		
		bool a;//comment cho một dòng
		if (x>a) then {
			int c=1;
		} else {
			y=x;
			x=x+1;
		}
		print(a);
		if (x>=a) then{
		x=x+1;
		}
		bool x=a==b;
		do  {
			int b=1;
			b=b*10;
			a=(b+10)*b;
		} while (a>1);
		print(a+1);
	end

Yêu cầu:

- Đề xuất văn phạm phi ngữ cảnh

- Xây dựng bộ phân tích từ vựng ( Lexer)
  
1.Program → begin Statements END

2.END → end

3.Statements → Statement Statements ∣ ε

4.Statement → Assignment ∣ Print ∣ Declaration ∣ Loop ∣ IfStatement

5.Declaration → Type Id DecTail

6.DecTail → =DecVal ∣ ε

7.DecVal → Boolean ∣ Comparison

8.Assignment → Id AssignTail // Toán tử gán '=' có độ ưu tiên thấp nhất

9.AssignTail → =AssignVal

10.AssignVal → Boolean ∣ Comparison

11.Block → { Statements }

12.ParenComp → ( Comparison )

13.IfStatement → if ParenComp then Block ElsePart

14.ElsePart → else Block ∣ ε

15.Loop → DoWhile ∣ ForLoop

16.DoWhile → do Block WHILE ParenComp ;

17.ForLoop → for ( ForInit ForCond ForUpdate ) Block

18.ForInit → Assignment ∣ Declaration ;

19.ForCond → Comparison ;

20.ForUpdate → Assignment

21.Print → print ( Comparison )

22.Comparison → Addition CompTail // So sánh có độ ưu tiên cao hơn gán '='

23.CompTail → CompOp Addition ∣ ε

24.CompOp → GT ∣ GE ∣ EQ

25.GT → >

26.GE → >=

27.EQ → ==

28.Addition → Multiplication AddTail // Phép cộng có độ ưu tiên cao hơn so sánh

29.AddTail → + Multiplication AddTail ∣ ε

30.Multiplication → Factor MulTail // Nhân có độ ưu tiên cao hơn cộng

31.MulTail → * Factor MulTail ∣ ε


32.Factor → ( Addition ) // Hỗ trợ dấu ngoặc
            ∣ Id
            ∣ Num
			
33.Type → int ∣ bool

34.Boolean → true ∣ false

35.Id → [a-zA-Z][a-zA-Z0-9]*

36.Num → [-]?[0-9]+

37.Comment → /* Text */
            ∣ // Text

38.Text → [a-zA-Z0-9]*

BTL02

Yêu cầu cơ bản: Xây dựng bộ phân tích cú pháp (Parser) cho ngôn ngữ UPL (xem lại BTL01).

- Kiểm tra được chương trình đúng/sai cú pháp: 6 điểm

- Báo lỗi rõ ràng, kèm thông tin giúp sửa lỗi: 1 điểm

- In ra được cây phân tích cú pháp: 1 điểm

Yêu cầu nâng cao:

- Có cả bộ phân tích cú pháp sinh tự động bằng các công cụ (CUP, Bison,...): +1 điểm

- Báo nhiều lỗi: +1 điểm

- Sử dụng cả phân tích từ trên xuống và dưới lên: +2 điểm

  
