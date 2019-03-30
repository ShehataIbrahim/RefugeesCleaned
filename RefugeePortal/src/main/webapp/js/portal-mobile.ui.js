$(function () {

	// 入力が行われたタイミングで毎度更新
	$('textarea').on('input', e => resizeTextarea(e.target))
	
	// 全てのtextareaを初期値で更新
	$('textarea').map((_, elm) => resizeTextarea(elm))
});


// textareaのvalueに応じて高さを更新する関数
function resizeTextarea(dom) {

	const target = $(dom)
	const { scrollHeight } = target[0]

	if (scrollHeight > target.innerHeight()) {
		target.innerHeight(scrollHeight)
	} else {
		const lineHeight = parseInt(target.css('lineHeight'))
		do {
			target.innerHeight(target.innerHeight() - lineHeight)
		} while (target.innerHeight() >= target[0].scrollHeight)
		target.innerHeight(target.innerHeight() + lineHeight)
	}
}

// facebook script
window.fbAsyncInit = function() {
  FB.init({
    appId      : '2102970179994843',
    cookie     : true,
    xfbml      : true,
    version    : 'v3.2'
  });
    
  FB.AppEvents.logPageView();   
    
};

(function(d, s, id){
   var js, fjs = d.getElementsByTagName(s)[0];
   if (d.getElementById(id)) {return;}
   js = d.createElement(s); js.id = id;
   js.src = "https://connect.facebook.net/en_US/sdk.js";
   fjs.parentNode.insertBefore(js, fjs);
 }(document, 'script', 'facebook-jssdk'));