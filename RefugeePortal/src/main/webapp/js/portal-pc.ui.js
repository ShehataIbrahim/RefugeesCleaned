$(function() {
	var hash = location.hash;
	var tabId = 1
    // 初期表示
	if(hash === "#tab1"){
	    tabId = 1
	}else if(hash === "#tab2"){
	    tabId = 2
	}else if(hash === "#tab3"){
	    tabId = 3
	}else if(hash === "#tab4"){
	    tabId = 4
	}else if(hash === "#tab5"){
	    tabId = 5
	}
    $('.tab-menu li:nth-child(' + tabId + ')').attr('id', 'menu-selected');
    $('.list-area').load('screening_list.html?c_id=' + tabId);

	// クリックしたタブをアクティブにしてリストをロード
	$('.tab-menu li').click(function() {
		var index = $('.tab-menu li').index(this);
		$('.tab-menu li').removeAttr('id');
		$(this).attr('id', 'menu-selected');
		$('.list-area').empty();

		switch (index) {
			case 0:
				$('.list-area').load('screening_list.html?c_id=1');
				break;
			case 1:
				$('.list-area').load('screening_list.html?c_id=2');
				break;
			case 2:
				$('.list-area').load('screening_list.html?c_id=3');
				break;
			case 3:
				$('.list-area').load('screening_list.html?c_id=4');
				break;
			case 4:
				$('.list-area').load('screening_list.html?c_id=5');
				break;
			default:
		}
	});
});
