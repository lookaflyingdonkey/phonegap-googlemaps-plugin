//
//  GroundOverlay.m
//  SimpleMap
//
//  Created by Katsumata Masashi on 12/4/13.
//
//

#import "IframeOverlay.h"


@implementation IframeOverlay

-(void)setGoogleMapsViewController:(GoogleMapsViewController *)viewCtrl
{
  self.mapCtrl = viewCtrl;
}

-(void)createIframeOverlay:(CDVInvokedUrlCommand *)command
{
  //NSDictionary *json = [command.arguments objectAtIndex:1];
  
  
  CGRect bounds = CGRectMake(0, 0, 100, 50);
  UIWebView *iframeView = [[UIWebView alloc] initWithFrame:bounds];
  iframeView.delegate = self.webView.delegate;
  [self.mapCtrl.view addSubview:iframeView];
  
  
  NSURL *appURL = [NSURL fileURLWithPath:[[NSBundle mainBundle] pathForResource:@"www/pages/hoge" ofType:@"html"]];
  NSLog(@"%@", appURL);
  NSURLRequest *request = [NSURLRequest requestWithURL:appURL cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:20.0];
  [iframeView loadRequest:request];
}

@end
