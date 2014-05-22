//
//  GroundOverlay.h
//  SimpleMap
//
//  Created by Katsumata Masashi on 12/4/13.
//
//

#import "GoogleMaps.h"
#import "MyPlgunProtocol.h"
#import "PluginUtil.h"

@interface IframeOverlay : CDVPlugin<MyPlgunProtocol>

@property (nonatomic, strong) GoogleMapsViewController* mapCtrl;

- (void)createIframeOverlay:(CDVInvokedUrlCommand*)command;


@end
