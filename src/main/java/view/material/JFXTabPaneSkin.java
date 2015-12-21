package view.material;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.scene.control.MultiplePropertyChangeListenerHandler;
import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.css.*;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.svg.SVGGlyph;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.scene.control.MultiplePropertyChangeListenerHandler;
import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Callback;
import javafx.util.Duration;

public class JFXTabPaneSkin extends BehaviorSkinBase<TabPane, TabPaneBehavior> {
    private Color defaultColor = Color.valueOf("#303F9F");
    private Color ripplerColor = Color.WHITE;
    private Color selectedTabText;
    private Color unSelectedTabText;
    private ObjectProperty<TabAnimation> openTabAnimation;
    private ObjectProperty<JFXTabPaneSkin.TabAnimation> closeTabAnimation;
    private static final double ANIMATION_SPEED = 150.0D;
    private static final int SPACER = 10;
    private JFXTabPaneSkin.TabHeaderArea tabHeaderArea;
    private ObservableList<TabContentRegion> tabContentRegions;
    private Rectangle clipRect;
    private Rectangle tabHeaderAreaClipRect;
    private Tab selectedTab;
    private boolean isSelectingTab;
    private boolean isDragged;
    private double dragStart;
    private double offsetStart;
    private double maxw;
    private double maxh;
    private int diffTabsIndices;
    private static int CLOSE_LBL_SIZE = 12;
    private static final PseudoClass SELECTED_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("selected");
    private static final PseudoClass TOP_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("top");
    private static final PseudoClass BOTTOM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("bottom");
    private static final PseudoClass LEFT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("left");
    private static final PseudoClass RIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("right");
    private static final PseudoClass DISABLED_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("disabled");
    private AnchorPane tabsContainer;
    private AnchorPane tabsContainerHolder;

    private static int getRotation(Side var0) {
        /*switch(null.$SwitchMap$javafx$geometry$Side[var0.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 180;
            case 3:
                return -90;
            case 4:
                return 90;
            default:
                return 0;
        }*/
        return 0;
    }

    private static Node clone(Node var0) {
        if(var0 == null) {
            return null;
        } else if(var0 instanceof ImageView) {
            ImageView var3 = (ImageView)var0;
            ImageView var4 = new ImageView();
            var4.setImage(var3.getImage());
            return var4;
        } else if(var0 instanceof Label) {
            Label var1 = (Label)var0;
            Label var2 = new Label(var1.getText(), var1.getGraphic());
            return var2;
        } else {
            return null;
        }
    }

    public JFXTabPaneSkin(TabPane var1) {
        super(var1, new TabPaneBehavior(var1));
        this.selectedTabText = Color.WHITE;
        this.unSelectedTabText = Color.LIGHTGREY;
        this.openTabAnimation = new StyleableObjectProperty(JFXTabPaneSkin.TabAnimation.GROW) {
            public CssMetaData<TabPane, TabAnimation> getCssMetaData() {
                return JFXTabPaneSkin.StyleableProperties.OPEN_TAB_ANIMATION;
            }

            public Object getBean() {
                return JFXTabPaneSkin.this;
            }

            public String getName() {
                return "openTabAnimation";
            }
        };
        this.closeTabAnimation = new StyleableObjectProperty(JFXTabPaneSkin.TabAnimation.GROW) {
            public CssMetaData<TabPane, JFXTabPaneSkin.TabAnimation> getCssMetaData() {
                return JFXTabPaneSkin.StyleableProperties.CLOSE_TAB_ANIMATION;
            }

            public Object getBean() {
                return JFXTabPaneSkin.this;
            }

            public String getName() {
                return "closeTabAnimation";
            }
        };
        this.maxw = 0.0D;
        this.maxh = 0.0D;
        this.diffTabsIndices = 0;
        this.clipRect = new Rectangle(var1.getWidth(), var1.getHeight());
        this.tabContentRegions = FXCollections.observableArrayList();
        this.tabsContainer = new AnchorPane();
        this.tabsContainerHolder = new AnchorPane();
        Iterator var2 = ((TabPane)this.getSkinnable()).getTabs().iterator();

        while(var2.hasNext()) {
            Tab var3 = (Tab)var2.next();
            this.addTabContent(var3);
        }

        this.tabHeaderAreaClipRect = new Rectangle();
        this.tabHeaderArea = new JFXTabPaneSkin.TabHeaderArea();
        this.tabHeaderArea.setClip(this.tabHeaderAreaClipRect);
        this.getChildren().add(this.tabHeaderArea);
        JFXDepthManager.setDepth(this.tabHeaderArea, 1);
        this.tabsContainerHolder.getChildren().add(this.tabsContainer);
        this.getChildren().add(this.tabsContainerHolder);
        if(((TabPane)this.getSkinnable()).getTabs().size() == 0) {
            this.tabHeaderArea.setVisible(false);
        }

        this.initializeTabListener();
        this.registerChangeListener(var1.getSelectionModel().selectedItemProperty(), "SELECTED_TAB");
        this.registerChangeListener(var1.sideProperty(), "SIDE");
        this.registerChangeListener(var1.widthProperty(), "WIDTH");
        this.registerChangeListener(var1.heightProperty(), "HEIGHT");
        this.selectedTab = (Tab)((TabPane)this.getSkinnable()).getSelectionModel().getSelectedItem();
        if(this.selectedTab == null && ((TabPane)this.getSkinnable()).getSelectionModel().getSelectedIndex() != -1) {
            ((TabPane)this.getSkinnable()).getSelectionModel().select(((TabPane)this.getSkinnable()).getSelectionModel().getSelectedIndex());
            this.selectedTab = (Tab)((TabPane)this.getSkinnable()).getSelectionModel().getSelectedItem();
        }

        if(this.selectedTab == null) {
            ((TabPane)this.getSkinnable()).getSelectionModel().selectFirst();
        }

        this.selectedTab = (Tab)((TabPane)this.getSkinnable()).getSelectionModel().getSelectedItem();
        this.isSelectingTab = false;
        this.initializeSwipeHandlers();
        this.tabHeaderArea.headersRegion.setOnMouseDragged((v1) -> {
            this.isDragged = true;
            this.tabHeaderArea.setScrollOffset(this.offsetStart + v1.getSceneX() - this.dragStart);
        });
        ((TabPane)this.getSkinnable()).setOnMousePressed((v1) -> {
            this.dragStart = v1.getSceneX();
            this.offsetStart = this.tabHeaderArea.getScrollOffset();
        });
        ((TabPane)this.getSkinnable()).setOnMouseReleased((var0) -> {
        });
    }

    public StackPane getSelectedTabContentRegion() {
        Iterator var1 = this.tabContentRegions.iterator();

        JFXTabPaneSkin.TabContentRegion var2;
        do {
            if(!var1.hasNext()) {
                return null;
            }

            var2 = (JFXTabPaneSkin.TabContentRegion)var1.next();
        } while(!var2.getTab().equals(this.selectedTab));

        return var2;
    }

    protected void handleControlPropertyChanged(String var1) {
        super.handleControlPropertyChanged(var1);
        if("SELECTED_TAB".equals(var1)) {
            this.isSelectingTab = true;
            this.selectedTab = (Tab)((TabPane)this.getSkinnable()).getSelectionModel().getSelectedItem();
            ((TabPane)this.getSkinnable()).requestLayout();
        } else if("SIDE".equals(var1)) {
            this.updateTabPosition();
        } else if("WIDTH".equals(var1)) {
            this.clipRect.setWidth(((TabPane)this.getSkinnable()).getWidth());
        } else if("HEIGHT".equals(var1)) {
            this.clipRect.setHeight(((TabPane)this.getSkinnable()).getHeight());
        }

    }

    private void removeTabs(List<? extends Tab> var1) {
        Iterator var2 = var1.iterator();

        while(var2.hasNext()) {
            Tab var3 = (Tab)var2.next();
            JFXTabPaneSkin.TabHeaderSkin var4 = this.tabHeaderArea.getTabHeaderSkin(var3);
            if(var4 != null) {
                var4.isClosing = true;
                if(this.closeTabAnimation.get() == JFXTabPaneSkin.TabAnimation.GROW) {
                    Timeline var5 = this.createTimeline(var4, Duration.millis(150.0D), 0.0D, (v2) -> {
                        this.handleClosedTab(var3);
                    });
                    var5.play();
                } else {
                    this.handleClosedTab(var3);
                }
            }
        }

    }

    private void handleClosedTab(Tab var1) {
        this.removeTab(var1);
        if(((TabPane)this.getSkinnable()).getTabs().isEmpty()) {
            this.tabHeaderArea.setVisible(false);
        }

    }

    private void addTabs(List<? extends Tab> var1, int var2) {
        int var3 = 0;
        Iterator var4 = var1.iterator();

        while(var4.hasNext()) {
            Tab var5 = (Tab)var4.next();
            if(!this.tabHeaderArea.isVisible()) {
                this.tabHeaderArea.setVisible(true);
            }

            int var6 = var2 + var3++;
            this.tabHeaderArea.addTab(var5, var6, false);
            this.addTabContent(var5);
            JFXTabPaneSkin.TabHeaderSkin var7 = this.tabHeaderArea.getTabHeaderSkin(var5);
            if(var7 != null) {
                if(this.openTabAnimation.get() == JFXTabPaneSkin.TabAnimation.GROW) {
                    var7.animationTransition.setValue(Double.valueOf(0.0D));
                    var7.setVisible(true);
                    this.createTimeline(var7, Duration.millis(150.0D), 1.0D, (v1) -> {
                        var7.inner.requestLayout();
                    }).play();
                } else {
                    var7.setVisible(true);
                    var7.inner.requestLayout();
                }
            }
        }

    }

    private void initializeTabListener() {
        ((TabPane)this.getSkinnable()).getTabs().addListener((ListChangeListener<? super Tab>) (var1) -> {
            ArrayList var2 = new ArrayList();
            ArrayList var3 = new ArrayList();
            int var4 = -1;

            while(var1.next()) {
                if(var1.wasPermutated()) {
                    TabPane var5 = (TabPane)this.getSkinnable();
                    ObservableList var6 = var5.getTabs();
                    int var7 = var1.getTo() - var1.getFrom();
                    Tab var8 = (Tab)var5.getSelectionModel().getSelectedItem();
                    ArrayList var9 = new ArrayList(var7);
                    ((TabPane)this.getSkinnable()).getSelectionModel().clearSelection();
                    TabAnimation var10 = (TabAnimation)this.openTabAnimation.get();
                    TabAnimation var11 = (TabAnimation)this.closeTabAnimation.get();
                    this.openTabAnimation.set(TabAnimation.NONE);
                    this.closeTabAnimation.set(TabAnimation.NONE);

                    for(int var12 = var1.getFrom(); var12 < var1.getTo(); ++var12) {
                        var9.add(var6.get(var12));
                    }

                    this.removeTabs(var9);
                    this.addTabs(var9, var1.getFrom());
                    this.openTabAnimation.set(var10);
                    this.closeTabAnimation.set(var11);
                    ((TabPane)this.getSkinnable()).getSelectionModel().select(var8);
                }

                if(var1.wasRemoved()) {
                    var2.addAll(var1.getRemoved());
                }

                if(var1.wasAdded()) {
                    var3.addAll(var1.getAddedSubList());
                    var4 = var1.getFrom();
                }
            }

            var2.removeAll(var3);
            this.removeTabs(var2);
            if(!var3.isEmpty()) {
                Iterator var13 = this.tabContentRegions.iterator();

                while(var13.hasNext()) {
                    TabContentRegion var14 = (TabContentRegion)var13.next();
                    Tab var15 = var14.getTab();
                    TabHeaderSkin var16 = this.tabHeaderArea.getTabHeaderSkin(var15);
                    if(!var16.isClosing && var3.contains(var14.getTab())) {
                        var3.remove(var14.getTab());
                    }
                }

                this.addTabs(var3, var4 == -1?this.tabContentRegions.size():var4);
            }

            ((TabPane)this.getSkinnable()).requestLayout();
        });
    }

    private void addTabContent(Tab var1) {
        JFXTabPaneSkin.TabContentRegion var2 = new JFXTabPaneSkin.TabContentRegion(var1);
        var2.setClip(new Rectangle());
        this.tabContentRegions.add(var2);
        this.tabsContainer.getChildren().add(0, var2);
    }

    private void removeTabContent(Tab var1) {
        Iterator var2 = this.tabContentRegions.iterator();

        while(var2.hasNext()) {
            JFXTabPaneSkin.TabContentRegion var3 = (JFXTabPaneSkin.TabContentRegion)var2.next();
            if(var3.getTab().equals(var1)) {
                var3.removeListeners(var1);
                this.getChildren().remove(var3);
                this.tabContentRegions.remove(var3);
                this.tabsContainer.getChildren().remove(var3);
                break;
            }
        }

    }

    private void removeTab(Tab var1) {
        JFXTabPaneSkin.TabHeaderSkin var2 = this.tabHeaderArea.getTabHeaderSkin(var1);
        if(var2 != null) {
            var2.removeListeners(var1);
        }

        this.tabHeaderArea.removeTab(var1);
        this.removeTabContent(var1);
        this.tabHeaderArea.requestLayout();
    }

    private void updateTabPosition() {
        this.tabHeaderArea.setScrollOffset(0.0D);
        ((TabPane)this.getSkinnable()).applyCss();
        ((TabPane)this.getSkinnable()).requestLayout();
    }

    private Timeline createTimeline(JFXTabPaneSkin.TabHeaderSkin var1, Duration var2, double var3, EventHandler<ActionEvent> var5) {
        Timeline var6 = new Timeline();
        var6.setCycleCount(1);
        KeyValue var7 = new KeyValue(var1.animationTransition, Double.valueOf(var3), Interpolator.LINEAR);
        var6.getKeyFrames().clear();
        var6.getKeyFrames().add(new KeyFrame(var2, var5, new KeyValue[]{var7}));
        return var6;
    }

    private boolean isHorizontal() {
        Side var1 = ((TabPane)this.getSkinnable()).getSide();
        return Side.TOP.equals(var1) || Side.BOTTOM.equals(var1);
    }

    private void initializeSwipeHandlers() {
        if(IS_TOUCH_SUPPORTED) {
            ((TabPane)this.getSkinnable()).addEventHandler(SwipeEvent.SWIPE_LEFT, (var1) -> {
                ((TabPaneBehavior)this.getBehavior()).selectNextTab();
            });
            ((TabPane)this.getSkinnable()).addEventHandler(SwipeEvent.SWIPE_RIGHT, (var1) -> {
                ((TabPaneBehavior)this.getBehavior()).selectPreviousTab();
            });
        }

    }

    private boolean isFloatingStyleClass() {
        return ((TabPane)this.getSkinnable()).getStyleClass().contains("floating");
    }

    protected double computePrefWidth(double var1, double var3, double var5, double var7, double var9) {
        JFXTabPaneSkin.TabContentRegion var12;
        for(Iterator var11 = this.tabContentRegions.iterator(); var11.hasNext(); this.maxw = Math.max(this.maxw, this.snapSize(var12.prefWidth(-1.0D)))) {
            var12 = (JFXTabPaneSkin.TabContentRegion)var11.next();
        }

        boolean var16 = this.isHorizontal();
        double var17 = this.snapSize(var16?this.tabHeaderArea.prefWidth(-1.0D):this.tabHeaderArea.prefHeight(-1.0D));
        double var14 = var16?Math.max(this.maxw, var17):this.maxw + var17;
        return this.snapSize(var14) + var5 + var9;
    }

    protected double computePrefHeight(double var1, double var3, double var5, double var7, double var9) {
        JFXTabPaneSkin.TabContentRegion var12;
        for(Iterator var11 = this.tabContentRegions.iterator(); var11.hasNext(); this.maxh = Math.max(this.maxh, this.snapSize(var12.prefHeight(-1.0D)))) {
            var12 = (JFXTabPaneSkin.TabContentRegion)var11.next();
        }

        boolean var16 = this.isHorizontal();
        double var17 = this.snapSize(var16?this.tabHeaderArea.prefHeight(-1.0D):this.tabHeaderArea.prefWidth(-1.0D));
        double var14 = var16?this.maxh + this.snapSize(var17):Math.max(this.maxh, var17);
        return this.snapSize(var14) + var3 + var7;
    }

    public double computeBaselineOffset(double var1, double var3, double var5, double var7) {
        Side var9 = ((TabPane)this.getSkinnable()).getSide();
        return var9 == Side.TOP?this.tabHeaderArea.getBaselineOffset() + var1:0.0D;
    }

    protected void layoutChildren(double var1, double var3, double var5, double var7) {
        TabPane var9 = (TabPane)this.getSkinnable();
        Side var10 = var9.getSide();
        double var11 = this.snapSize(this.tabHeaderArea.prefHeight(-1.0D));
        double var13 = var10.equals(Side.RIGHT)?var1 + var5 - var11:var1;
        double var15 = var10.equals(Side.BOTTOM)?var3 + var7 - var11:var3;
        if(var10 == Side.TOP) {
            this.tabHeaderArea.resize(var5, var11);
            this.tabHeaderArea.relocate(var13, var15);
            this.tabHeaderArea.getTransforms().clear();
            this.tabHeaderArea.getTransforms().add(new Rotate((double)getRotation(Side.TOP)));
        } else if(var10 == Side.BOTTOM) {
            this.tabHeaderArea.resize(var5, var11);
            this.tabHeaderArea.relocate(var5, var15 - var11);
            this.tabHeaderArea.getTransforms().clear();
            this.tabHeaderArea.getTransforms().add(new Rotate((double)getRotation(Side.BOTTOM), 0.0D, var11));
        } else if(var10 == Side.LEFT) {
            this.tabHeaderArea.resize(var7, var11);
            this.tabHeaderArea.relocate(var13 + var11, var7 - var11);
            this.tabHeaderArea.getTransforms().clear();
            this.tabHeaderArea.getTransforms().add(new Rotate((double)getRotation(Side.LEFT), 0.0D, var11));
        } else if(var10 == Side.RIGHT) {
            this.tabHeaderArea.resize(var7, var11);
            this.tabHeaderArea.relocate(var13, var3 - var11);
            this.tabHeaderArea.getTransforms().clear();
            this.tabHeaderArea.getTransforms().add(new Rotate((double)getRotation(Side.RIGHT), 0.0D, var11));
        }

        this.tabHeaderAreaClipRect.setX(0.0D);
        this.tabHeaderAreaClipRect.setY(0.0D);
        if(this.isHorizontal()) {
            this.tabHeaderAreaClipRect.setWidth(var5);
        } else {
            this.tabHeaderAreaClipRect.setWidth(var7);
        }

        this.tabHeaderAreaClipRect.setHeight(var11 + 7.0D);
        double var17 = 0.0D;
        double var19 = 0.0D;
        if(var10 == Side.TOP) {
            var17 = var1;
            var19 = var3 + var11;
            if(this.isFloatingStyleClass()) {
                --var19;
            }
        } else if(var10 == Side.BOTTOM) {
            var17 = var1;
            var19 = var3;
            if(this.isFloatingStyleClass()) {
                var19 = 1.0D;
            }
        } else if(var10 == Side.LEFT) {
            var17 = var1 + var11;
            var19 = var3;
            if(this.isFloatingStyleClass()) {
                --var17;
            }
        } else if(var10 == Side.RIGHT) {
            var17 = var1;
            var19 = var3;
            if(this.isFloatingStyleClass()) {
                var17 = 1.0D;
            }
        }

        double var21 = var5 - (this.isHorizontal()?0.0D:var11);
        double var23 = var7 - (this.isHorizontal()?var11:0.0D);
        Rectangle var25 = new Rectangle(var21, var23);
        this.tabsContainerHolder.setClip(var25);
        this.tabsContainerHolder.resize(var21, var23);
        this.tabsContainerHolder.relocate(var17, var19);
        this.tabsContainer.resize(var21 * (double)this.tabContentRegions.size(), var23);
        int var26 = 0;

        for(int var27 = this.tabContentRegions.size(); var26 < var27; ++var26) {
            JFXTabPaneSkin.TabContentRegion var28 = (JFXTabPaneSkin.TabContentRegion)this.tabContentRegions.get(var26);
            var28.setVisible(true);
            var28.setTranslateX(var21 * (double)var26);
            if(var28.getClip() != null) {
                ((Rectangle)var28.getClip()).setWidth(var21);
                ((Rectangle)var28.getClip()).setHeight(var23);
            }

            if(var28.getTab() == this.selectedTab) {
                int var29 = ((TabPane)this.getSkinnable()).getTabs().indexOf(this.selectedTab);
                if(var29 != var26) {
                    this.tabsContainer.setTranslateX(-var21 * (double)var26);
                    this.diffTabsIndices = var26 - var29;
                } else {
                    if(this.diffTabsIndices != 0) {
                        this.tabsContainer.setTranslateX(this.tabsContainer.getTranslateX() + var21 * (double)this.diffTabsIndices);
                        this.diffTabsIndices = 0;
                    }

                    if(this.isSelectingTab) {
                        (new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(320.0D), new KeyValue[]{new KeyValue(this.tabsContainer.translateXProperty(), Double.valueOf(-var21 * (double)var29), Interpolator.EASE_BOTH)})})).play();
                    } else {
                        this.tabsContainer.setTranslateX(-var21 * (double)var29);
                    }
                }
            }

            var28.resize(var21, var23);
        }

    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return JFXTabPaneSkin.StyleableProperties.STYLEABLES;
    }

    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    class TabMenuItem extends RadioMenuItem {
        Tab tab;
        private InvalidationListener disableListener = new InvalidationListener() {
            public void invalidated(Observable var1) {
                TabMenuItem.this.setDisable(TabMenuItem.this.tab.isDisable());
            }
        };
        private WeakInvalidationListener weakDisableListener;

        public TabMenuItem(Tab var2) {
            super(var2.getText(), JFXTabPaneSkin.clone(var2.getGraphic()));
            this.weakDisableListener = new WeakInvalidationListener(this.disableListener);
            this.tab = var2;
            this.setDisable(var2.isDisable());
            var2.disableProperty().addListener(this.weakDisableListener);
        }

        public Tab getTab() {
            return this.tab;
        }

        public void dispose() {
            this.tab.disableProperty().removeListener(this.weakDisableListener);
        }
    }

    class TabControlButtons extends StackPane {
        private StackPane inner;
        private boolean showControlButtons;
        private boolean isLeftArrow;
        private Timeline arrowAnimation;
        private SVGGlyph arrowButton;
        private SVGGlyph leftChevron;
        private SVGGlyph rightChevron;
        private boolean showTabsMenu;

        public TabControlButtons(JFXTabPaneSkin.ArrowPosition var2) {
            this.leftChevron = new SVGGlyph(0, "CHEVRON_LEFT", "M 742,-37 90,614 Q 53,651 53,704.5 53,758 90,795 l 652,651 q 37,37 90.5,37 53.5,0 90.5,-37 l 75,-75 q 37,-37 37,-90.5 0,-53.5 -37,-90.5 L 512,704 998,219 q 37,-38 37,-91 0,-53 -37,-90 L 923,-37 Q 886,-74 832.5,-74 779,-74 742,-37 z", Color.WHITE);
            this.rightChevron = new SVGGlyph(0, "CHEVRON_RIGHT", "m 1099,704 q 0,-52 -37,-91 L 410,-38 q -37,-37 -90,-37 -53,0 -90,37 l -76,75 q -37,39 -37,91 0,53 37,90 l 486,486 -486,485 q -37,39 -37,91 0,53 37,90 l 76,75 q 36,38 90,38 54,0 90,-38 l 652,-651 q 37,-37 37,-90 z", Color.WHITE);
            this.showTabsMenu = false;
            this.getStyleClass().setAll(new String[]{"control-buttons-tab"});
            this.isLeftArrow = var2 == JFXTabPaneSkin.ArrowPosition.LEFT;
            this.arrowButton = this.isLeftArrow?this.leftChevron:this.rightChevron;
            this.arrowButton.setStyle("-fx-min-width:0.8em;-fx-max-width:0.8em;-fx-min-height:1.3em;-fx-max-height:1.3em;");
            this.arrowButton.getStyleClass().setAll(new String[]{"tab-down-button"});
            this.arrowButton.setVisible(this.isShowTabsMenu());
            this.arrowButton.setFill(JFXTabPaneSkin.this.selectedTabText);
            SimpleDoubleProperty var3 = new SimpleDoubleProperty(0.0D);
            var3.addListener((ObservableValue<? extends Number> var1, Number v2, Number v3) -> {
                JFXTabPaneSkin.this.tabHeaderArea.setScrollOffset(v3.doubleValue());
            });
            StackPane var4 = new StackPane(new Node[]{this.arrowButton});
            var4.getStyleClass().add("container");
            var4.setPadding(new Insets(7.0D));
            var4.setCursor(Cursor.HAND);
            var4.setOnMousePressed((v2) -> {
                var3.set(JFXTabPaneSkin.this.tabHeaderArea.getScrollOffset());
                double var3x = this.isLeftArrow?JFXTabPaneSkin.this.tabHeaderArea.getScrollOffset() + JFXTabPaneSkin.this.tabHeaderArea.headersRegion.getWidth():JFXTabPaneSkin.this.tabHeaderArea.getScrollOffset() - JFXTabPaneSkin.this.tabHeaderArea.headersRegion.getWidth();
                this.arrowAnimation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0D), new KeyValue[]{new KeyValue(var3, Double.valueOf(var3x), Interpolator.LINEAR)})});
                this.arrowAnimation.play();
            });
            var4.setOnMouseReleased((var1) -> {
                this.arrowAnimation.stop();
            });
            final JFXRippler var5 = new JFXRippler(var4, JFXRippler.RipplerMask.CIRCLE, JFXRippler.RipplerPos.BACK);
            var5.ripplerFillProperty().bind(this.arrowButton.fillProperty());
            StackPane.setMargin(this.arrowButton, new Insets(0.0D, 0.0D, 0.0D, this.isLeftArrow?-4.0D:4.0D));
            this.inner = new StackPane() {
                protected double computePrefWidth(double var1) {
                    double var5x = !TabControlButtons.this.isShowTabsMenu()?0.0D:this.snapSize(var5.prefWidth(this.getHeight()));
                    double var3 = 0.0D;
                    if(TabControlButtons.this.isShowTabsMenu()) {
                        var3 += var5x;
                    }

                    if(var3 > 0.0D) {
                        var3 += this.snappedLeftInset() + this.snappedRightInset();
                    }

                    return var3;
                }

                protected double computePrefHeight(double var1) {
                    double var3 = 0.0D;
                    if(TabControlButtons.this.isShowTabsMenu()) {
                        var3 = Math.max(var3, this.snapSize(var5.prefHeight(var1)));
                    }

                    if(var3 > 0.0D) {
                        var3 += this.snappedTopInset() + this.snappedBottomInset();
                    }

                    return var3;
                }

                protected void layoutChildren() {
                    if(TabControlButtons.this.isShowTabsMenu()) {
                        double var1 = 0.0D;
                        double var3 = this.snappedTopInset();
                        double var5x = this.snapSize(this.getWidth()) - var1 + this.snappedLeftInset();
                        double var7 = this.snapSize(this.getHeight()) - var3 + this.snappedBottomInset();
                        this.positionArrow(var5, var1, var3, var5x, var7);
                    }

                }

                private void positionArrow(JFXRippler var1, double var2, double var4, double var6, double var8) {
                    var1.resize(var6, var8);
                    this.positionInArea(var1, var2, var4, var6, var8, 0.0D, HPos.CENTER, VPos.CENTER);
                }
            };
            var5.setPadding(new Insets(0.0D, 5.0D, 0.0D, 5.0D));
            this.inner.getChildren().add(var5);
            StackPane.setMargin(var5, new Insets(0.0D, 4.0D, 0.0D, 4.0D));
            this.getChildren().add(this.inner);
            this.showControlButtons = false;
            if(this.isShowTabsMenu()) {
                this.showControlButtons = true;
                this.requestLayout();
            }

        }

        private void showTabsMenu(boolean var1) {
            boolean var2 = this.isShowTabsMenu();
            this.showTabsMenu = var1;
            if(this.showTabsMenu && !var2) {
                this.arrowButton.setVisible(true);
                this.showControlButtons = true;
                this.inner.requestLayout();
                JFXTabPaneSkin.this.tabHeaderArea.requestLayout();
            } else if(!this.showTabsMenu && var2) {
                this.hideControlButtons();
            }

        }

        private boolean isShowTabsMenu() {
            return this.showTabsMenu;
        }

        protected double computePrefWidth(double var1) {
            double var3 = this.snapSize(this.inner.prefWidth(var1));
            if(var3 > 0.0D) {
                var3 += this.snappedLeftInset() + this.snappedRightInset();
            }

            return var3;
        }

        protected double computePrefHeight(double var1) {
            return Math.max(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabMinHeight(), this.snapSize(this.inner.prefHeight(var1))) + this.snappedTopInset() + this.snappedBottomInset();
        }

        protected void layoutChildren() {
            double var1 = this.snappedLeftInset();
            double var3 = this.snappedTopInset();
            double var5 = this.snapSize(this.getWidth()) - var1 + this.snappedRightInset();
            double var7 = this.snapSize(this.getHeight()) - var3 + this.snappedBottomInset();
            if(this.showControlButtons) {
                this.showControlButtons();
                this.showControlButtons = false;
            }

            this.inner.resize(var5, var7);
            this.positionInArea(this.inner, var1, var3, var5, var7, 0.0D, HPos.CENTER, VPos.BOTTOM);
        }

        private void showControlButtons() {
            this.setVisible(true);
        }

        private void hideControlButtons() {
            if(this.isShowTabsMenu()) {
                this.showControlButtons = true;
            } else {
                this.setVisible(false);
            }

            this.requestLayout();
        }
    }

    private static enum ArrowPosition {
        RIGHT,
        LEFT;

        private ArrowPosition() {
        }
    }

    class TabContentRegion extends StackPane {
        private Tab tab;
        private InvalidationListener tabContentListener = (var1) -> {
            this.updateContent();
        };
        private InvalidationListener tabSelectedListener = new InvalidationListener() {
            public void invalidated(Observable var1) {
                TabContentRegion.this.setVisible(TabContentRegion.this.tab.isSelected());
            }
        };
        private WeakInvalidationListener weakTabContentListener;
        private WeakInvalidationListener weakTabSelectedListener;

        public Tab getTab() {
            return this.tab;
        }

        public TabContentRegion(Tab var2) {
            this.weakTabContentListener = new WeakInvalidationListener(this.tabContentListener);
            this.weakTabSelectedListener = new WeakInvalidationListener(this.tabSelectedListener);
            this.getStyleClass().setAll(new String[]{"tab-content-area"});
            this.setManaged(false);
            this.tab = var2;
            this.updateContent();
            this.setVisible(var2.isSelected());
            var2.selectedProperty().addListener(this.weakTabSelectedListener);
            var2.contentProperty().addListener(this.weakTabContentListener);
        }

        private void updateContent() {
            Node var1 = this.getTab().getContent();
            if(var1 == null) {
                this.getChildren().clear();
            } else {
                this.getChildren().setAll(new Node[]{var1});
            }

        }

        private void removeListeners(Tab var1) {
            var1.selectedProperty().removeListener(this.weakTabSelectedListener);
            var1.contentProperty().removeListener(this.weakTabContentListener);
        }
    }

    class TabHeaderSkin extends StackPane {
        private final Tab tab;
        private Label tabText;
        private BorderPane inner;
        private Tooltip oldTooltip;
        private Tooltip tooltip;
        private JFXRippler rippler;
        private boolean isClosing = false;
        private MultiplePropertyChangeListenerHandler listener = new MultiplePropertyChangeListenerHandler((var1) -> {
            this.handlePropertyChanged(var1);
            return null;
        });
        private final ListChangeListener<String> styleClassListener = new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TabHeaderSkin.this.getStyleClass().setAll(TabHeaderSkin.this.tab.getStyleClass());
            }
        };
        private final WeakListChangeListener<String> weakStyleClassListener;
        private final DoubleProperty animationTransition;

        public Tab getTab() {
            return this.tab;
        }

        public TabHeaderSkin(Tab var2) {
            this.weakStyleClassListener = new WeakListChangeListener(this.styleClassListener);
            this.animationTransition = new SimpleDoubleProperty(this, "animationTransition", 1.0D) {
                protected void invalidated() {
                    TabHeaderSkin.this.requestLayout();
                }
            };
            this.getStyleClass().setAll(var2.getStyleClass());
            this.setId(var2.getId());
            this.setStyle(var2.getStyle());
            this.tab = var2;
            this.tabText = new Label(var2.getText(), var2.getGraphic());
            this.tabText.setFont(Font.font("", FontWeight.NORMAL, 16.0D));
            this.tabText.setPadding(new Insets(5.0D, 10.0D, 5.0D, 10.0D));
            this.tabText.getStyleClass().setAll(new String[]{"tab-label"});
            this.updateGraphicRotation();
            this.inner = new BorderPane();
            this.inner.setCenter(this.tabText);
            this.inner.getStyleClass().add("tab-container");
            this.inner.setRotate(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide().equals(Side.BOTTOM)?180.0D:0.0D);
            this.rippler = new JFXRippler(this.inner, JFXRippler.RipplerPos.FRONT);
            this.rippler.setRipplerFill(JFXTabPaneSkin.this.ripplerColor);
            this.getChildren().addAll(new Node[]{this.rippler});
            this.tooltip = var2.getTooltip();
            if(this.tooltip != null) {
                Tooltip.install(this, this.tooltip);
                this.oldTooltip = this.tooltip;
            }

            if(var2.isSelected()) {
                this.tabText.setTextFill(JFXTabPaneSkin.this.selectedTabText);
            } else {
                this.tabText.setTextFill(JFXTabPaneSkin.this.unSelectedTabText);
            }

            var2.selectedProperty().addListener((var1, v2, var3) -> {
                if(var3.booleanValue()) {
                    this.tabText.setTextFill(JFXTabPaneSkin.this.selectedTabText);
                } else {
                    this.tabText.setTextFill(JFXTabPaneSkin.this.unSelectedTabText);
                }

            });
            this.listener.registerChangeListener(var2.closableProperty(), "CLOSABLE");
            this.listener.registerChangeListener(var2.selectedProperty(), "SELECTED");
            this.listener.registerChangeListener(var2.textProperty(), "TEXT");
            this.listener.registerChangeListener(var2.graphicProperty(), "GRAPHIC");
            this.listener.registerChangeListener(var2.contextMenuProperty(), "CONTEXT_MENU");
            this.listener.registerChangeListener(var2.tooltipProperty(), "TOOLTIP");
            this.listener.registerChangeListener(var2.disableProperty(), "DISABLE");
            this.listener.registerChangeListener(var2.styleProperty(), "STYLE");
            var2.getStyleClass().addListener(this.weakStyleClassListener);
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).tabClosingPolicyProperty(), "TAB_CLOSING_POLICY");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).sideProperty(), "SIDE");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).rotateGraphicProperty(), "ROTATE_GRAPHIC");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).tabMinWidthProperty(), "TAB_MIN_WIDTH");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).tabMaxWidthProperty(), "TAB_MAX_WIDTH");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).tabMinHeightProperty(), "TAB_MIN_HEIGHT");
            this.listener.registerChangeListener(((TabPane)JFXTabPaneSkin.this.getSkinnable()).tabMaxHeightProperty(), "TAB_MAX_HEIGHT");
            this.getProperties().put(Tab.class, var2);
            this.getProperties().put(ContextMenu.class, var2.getContextMenu());
            this.setOnContextMenuRequested((var1) -> {
                if(this.getTab().getContextMenu() != null) {
                    this.getTab().getContextMenu().show(this.inner, var1.getScreenX(), var1.getScreenY());
                    var1.consume();
                }

            });
            this.setOnMouseReleased(new EventHandler() {
                @Override
                public void handle(Event event) {
                    handle((MouseEvent) event);
                }

                public void handle(MouseEvent var1) {
                    if(JFXTabPaneSkin.this.isDragged) {
                        JFXTabPaneSkin.this.isDragged = false;
                    } else if(!TabHeaderSkin.this.getTab().isDisable()) {
                        if(var1.getButton().equals(MouseButton.MIDDLE)) {
                            if(TabHeaderSkin.this.showCloseButton()) {
                                Tab var2 = TabHeaderSkin.this.getTab();
                                TabPaneBehavior var3 = (TabPaneBehavior)JFXTabPaneSkin.this.getBehavior();
                                if(var3.canCloseTab(var2)) {
                                    TabHeaderSkin.this.removeListeners(var2);
                                    var3.closeTab(var2);
                                }
                            }
                        } else if(var1.getButton().equals(MouseButton.PRIMARY)) {
                            TabHeaderSkin.this.setOpacity(1.0D);
                            ((TabPaneBehavior)JFXTabPaneSkin.this.getBehavior()).selectTab(TabHeaderSkin.this.getTab());
                        }

                    }
                }
            });
            this.setOnMouseEntered((var2x) -> {
                if(!var2.isSelected()) {
                    this.setOpacity(0.7D);
                }

            });
            this.setOnMouseExited((var1) -> {
                this.setOpacity(1.0D);
            });
            this.pseudoClassStateChanged(JFXTabPaneSkin.SELECTED_PSEUDOCLASS_STATE, var2.isSelected());
            this.pseudoClassStateChanged(JFXTabPaneSkin.DISABLED_PSEUDOCLASS_STATE, var2.isDisable());
            Side var3 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
            this.pseudoClassStateChanged(JFXTabPaneSkin.TOP_PSEUDOCLASS_STATE, var3 == Side.TOP);
            this.pseudoClassStateChanged(JFXTabPaneSkin.RIGHT_PSEUDOCLASS_STATE, var3 == Side.RIGHT);
            this.pseudoClassStateChanged(JFXTabPaneSkin.BOTTOM_PSEUDOCLASS_STATE, var3 == Side.BOTTOM);
            this.pseudoClassStateChanged(JFXTabPaneSkin.LEFT_PSEUDOCLASS_STATE, var3 == Side.LEFT);
        }

        private void handlePropertyChanged(String var1) {
            if("CLOSABLE".equals(var1)) {
                this.inner.requestLayout();
                this.requestLayout();
            } else if("SELECTED".equals(var1)) {
                this.pseudoClassStateChanged(JFXTabPaneSkin.SELECTED_PSEUDOCLASS_STATE, this.tab.isSelected());
                this.inner.requestLayout();
                this.requestLayout();
            } else if("TEXT".equals(var1)) {
                this.tabText.setText(this.getTab().getText());
            } else if("GRAPHIC".equals(var1)) {
                this.tabText.setGraphic(this.getTab().getGraphic());
            } else if(!"CONTEXT_MENU".equals(var1)) {
                if("TOOLTIP".equals(var1)) {
                    if(this.oldTooltip != null) {
                        Tooltip.uninstall(this, this.oldTooltip);
                    }

                    this.tooltip = this.tab.getTooltip();
                    if(this.tooltip != null) {
                        Tooltip.install(this, this.tooltip);
                        this.oldTooltip = this.tooltip;
                    }
                } else if("DISABLE".equals(var1)) {
                    this.pseudoClassStateChanged(JFXTabPaneSkin.DISABLED_PSEUDOCLASS_STATE, this.tab.isDisable());
                    this.inner.requestLayout();
                    this.requestLayout();
                } else if("STYLE".equals(var1)) {
                    this.setStyle(this.tab.getStyle());
                } else if("TAB_CLOSING_POLICY".equals(var1)) {
                    this.inner.requestLayout();
                    this.requestLayout();
                } else if("SIDE".equals(var1)) {
                    Side var2 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
                    this.pseudoClassStateChanged(JFXTabPaneSkin.TOP_PSEUDOCLASS_STATE, var2 == Side.TOP);
                    this.pseudoClassStateChanged(JFXTabPaneSkin.RIGHT_PSEUDOCLASS_STATE, var2 == Side.RIGHT);
                    this.pseudoClassStateChanged(JFXTabPaneSkin.BOTTOM_PSEUDOCLASS_STATE, var2 == Side.BOTTOM);
                    this.pseudoClassStateChanged(JFXTabPaneSkin.LEFT_PSEUDOCLASS_STATE, var2 == Side.LEFT);
                    this.inner.setRotate(var2 == Side.BOTTOM?180.0D:0.0D);
                    if(((TabPane)JFXTabPaneSkin.this.getSkinnable()).isRotateGraphic()) {
                        this.updateGraphicRotation();
                    }
                } else if("ROTATE_GRAPHIC".equals(var1)) {
                    this.updateGraphicRotation();
                } else if("TAB_MIN_WIDTH".equals(var1)) {
                    this.requestLayout();
                    ((TabPane)JFXTabPaneSkin.this.getSkinnable()).requestLayout();
                } else if("TAB_MAX_WIDTH".equals(var1)) {
                    this.requestLayout();
                    ((TabPane)JFXTabPaneSkin.this.getSkinnable()).requestLayout();
                } else if("TAB_MIN_HEIGHT".equals(var1)) {
                    this.requestLayout();
                    ((TabPane)JFXTabPaneSkin.this.getSkinnable()).requestLayout();
                } else if("TAB_MAX_HEIGHT".equals(var1)) {
                    this.requestLayout();
                    ((TabPane)JFXTabPaneSkin.this.getSkinnable()).requestLayout();
                }
            }

        }

        private void updateGraphicRotation() {
            if(this.tabText.getGraphic() != null) {
                this.tabText.getGraphic().setRotate(((TabPane)JFXTabPaneSkin.this.getSkinnable()).isRotateGraphic()?0.0D:(double)(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide().equals(Side.RIGHT)?-90.0F:(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide().equals(Side.LEFT)?90.0F:0.0F)));
            }

        }

        private boolean showCloseButton() {
            return this.tab.isClosable() && (((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabClosingPolicy().equals(TabPane.TabClosingPolicy.ALL_TABS) || ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabClosingPolicy().equals(TabPane.TabClosingPolicy.SELECTED_TAB));
        }

        private void removeListeners(Tab var1) {
            this.listener.dispose();
            ContextMenu var2 = var1.getContextMenu();
            if(var2 != null) {
                var2.getItems().clear();
            }

            this.inner.getChildren().clear();
            this.getChildren().clear();
        }

        protected double computePrefWidth(double var1) {
            double var3 = this.snapSize(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabMinWidth());
            double var5 = this.snapSize(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabMaxWidth());
            double var7 = this.snappedRightInset();
            double var9 = this.snappedLeftInset();
            double var11 = this.snapSize(this.tabText.prefWidth(-1.0D));
            if(var11 > var5) {
                var11 = var5;
            } else if(var11 < var3) {
                var11 = var3;
            }

            var11 += var7 + var9;
            return var11;
        }

        protected double computePrefHeight(double var1) {
            double var3 = this.snapSize(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabMinHeight());
            double var5 = this.snapSize(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getTabMaxHeight());
            double var7 = this.snappedTopInset();
            double var9 = this.snappedBottomInset();
            double var11 = this.snapSize(this.tabText.prefHeight(var1));
            if(var11 > var5) {
                var11 = var5;
            } else if(var11 < var3) {
                var11 = var3;
            }

            var11 += var7 + var9;
            return var11;
        }

        protected void layoutChildren() {
            double var1 = (this.snapSize(this.getWidth()) - this.snappedRightInset() - this.snappedLeftInset()) * this.animationTransition.getValue().doubleValue();
            this.rippler.resize(var1, this.snapSize(this.getHeight()) - this.snappedTopInset() - this.snappedBottomInset());
            this.rippler.relocate(this.snappedLeftInset(), this.snappedTopInset());
        }

        protected void setWidth(double var1) {
            super.setWidth(var1);
        }

        protected void setHeight(double var1) {
            super.setHeight(var1);
        }
    }

    class TabHeaderArea extends StackPane {
        private Rectangle headerClip;
        private StackPane headersRegion;
        private StackPane headerBackground;
        private JFXTabPaneSkin.TabControlButtons rightControlButtons;
        private JFXTabPaneSkin.TabControlButtons leftControlButtons;
        private Line selectedTabLine;
        private boolean initialized;
        private boolean measureClosingTabs = false;
        private double scrollOffset;
        private double selectedTabLineOffset;
        private List<JFXTabPaneSkin.TabHeaderSkin> removeTab = new ArrayList();

        public TabHeaderArea() {
            this.getStyleClass().setAll(new String[]{"tab-header-area"});
            this.setManaged(false);
            TabPane var2 = (TabPane)JFXTabPaneSkin.this.getSkinnable();
            this.headerClip = new Rectangle();
            this.headersRegion = new StackPane() {
                protected double computePrefWidth(double var1) {
                    double var3 = 0.0D;
                    Iterator var5 = this.getChildren().iterator();

                    while(true) {
                        JFXTabPaneSkin.TabHeaderSkin var7;
                        do {
                            do {
                                Node var6;
                                do {
                                    if(!var5.hasNext()) {
                                        return this.snapSize(var3) + this.snappedLeftInset() + this.snappedRightInset();
                                    }

                                    var6 = (Node)var5.next();
                                } while(!(var6 instanceof JFXTabPaneSkin.TabHeaderSkin));

                                var7 = (JFXTabPaneSkin.TabHeaderSkin)var6;
                            } while(!var7.isVisible());
                        } while(!TabHeaderArea.this.measureClosingTabs && var7.isClosing);

                        var3 += var7.prefWidth(var1);
                    }
                }

                protected double computePrefHeight(double var1) {
                    double var3 = 0.0D;
                    Iterator var5 = this.getChildren().iterator();

                    while(var5.hasNext()) {
                        Node var6 = (Node)var5.next();
                        if(var6 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                            JFXTabPaneSkin.TabHeaderSkin var7 = (JFXTabPaneSkin.TabHeaderSkin)var6;
                            var3 = Math.max(var3, var7.prefHeight(var1));
                        }
                    }

                    return this.snapSize(var3) + this.snappedTopInset() + this.snappedBottomInset();
                }

                protected void layoutChildren() {
                    if(TabHeaderArea.this.tabsFit()) {
                        TabHeaderArea.this.setScrollOffset(0.0D);
                    } else if(!TabHeaderArea.this.removeTab.isEmpty()) {
                        double var1 = 0.0D;
                        double var3 = JFXTabPaneSkin.this.tabHeaderArea.getWidth() - this.snapSize(TabHeaderArea.this.rightControlButtons.prefWidth(-1.0D)) - this.snapSize(TabHeaderArea.this.leftControlButtons.prefWidth(-1.0D)) - TabHeaderArea.this.firstTabIndent() - 10.0D;
                        Iterator var5 = this.getChildren().iterator();

                        while(var5.hasNext()) {
                            Node var6 = (Node)var5.next();
                            if(var6 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                                JFXTabPaneSkin.TabHeaderSkin var7 = (JFXTabPaneSkin.TabHeaderSkin)var6;
                                double var8 = this.snapSize(var7.prefWidth(-1.0D));
                                if(TabHeaderArea.this.removeTab.contains(var7)) {
                                    if(var1 < var3) {
                                        JFXTabPaneSkin.this.isSelectingTab = true;
                                    }

                                    var5.remove();
                                    TabHeaderArea.this.removeTab.remove(var7);
                                    if(TabHeaderArea.this.removeTab.isEmpty()) {
                                        break;
                                    }
                                }

                                var1 += var8;
                            }
                        }
                    }

                    if(JFXTabPaneSkin.this.isSelectingTab) {
                        TabHeaderArea.this.ensureSelectedTabIsVisible();
                        JFXTabPaneSkin.this.isSelectingTab = false;
                    } else {
                        TabHeaderArea.this.validateScrollOffset();
                    }

                    Side var15 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
                    double var2 = this.snapSize(this.prefHeight(-1.0D));
                    double var4 = !var15.equals(Side.LEFT) && !var15.equals(Side.BOTTOM)?TabHeaderArea.this.getScrollOffset():this.snapSize(this.getWidth()) - TabHeaderArea.this.getScrollOffset();
                    TabHeaderArea.this.updateHeaderClip();
                    Iterator var16 = this.getChildren().iterator();

                    while(true) {
                        while(true) {
                            Node var17;
                            do {
                                if(!var16.hasNext()) {
                                    return;
                                }

                                var17 = (Node)var16.next();
                            } while(!(var17 instanceof JFXTabPaneSkin.TabHeaderSkin));

                            JFXTabPaneSkin.TabHeaderSkin var18 = (JFXTabPaneSkin.TabHeaderSkin)var17;
                            double var9 = this.snapSize(var18.prefWidth(-1.0D) * var18.animationTransition.get());
                            double var11 = this.snapSize(var18.prefHeight(-1.0D));
                            var18.resize(var9, var11);
                            double var13 = var15.equals(Side.BOTTOM)?0.0D:var2 - var11 - this.snappedBottomInset();
                            if(!var15.equals(Side.LEFT) && !var15.equals(Side.BOTTOM)) {
                                var18.relocate(var4, var13);
                                var4 += var9;
                            } else {
                                var4 -= var9;
                                var18.relocate(var4, var13);
                            }
                        }
                    }
                }
            };
            this.headersRegion.getStyleClass().setAll(new String[]{"headers-region"});
            this.headersRegion.setClip(this.headerClip);
            this.headerBackground = new StackPane();
            this.headerBackground.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(JFXTabPaneSkin.this.defaultColor, CornerRadii.EMPTY, Insets.EMPTY)}));
            this.headerBackground.getStyleClass().setAll(new String[]{"tab-header-background"});
            this.selectedTabLine = new Line();
            this.selectedTabLine.getStyleClass().add("tab-selected-line");
            this.selectedTabLine.setStrokeWidth(2.0D);
            this.selectedTabLine.setStroke(JFXTabPaneSkin.this.ripplerColor);
            this.headersRegion.getChildren().add(this.selectedTabLine);
            this.selectedTabLine.translateYProperty().bind(Bindings.createDoubleBinding(() -> {
                return Double.valueOf(this.headersRegion.getHeight() - this.selectedTabLine.getStrokeWidth() + 1.0D);
            }, new Observable[]{this.headersRegion.heightProperty(), this.selectedTabLine.strokeWidthProperty()}));
            int var3 = 0;
            Iterator var4 = var2.getTabs().iterator();

            while(var4.hasNext()) {
                Tab var5 = (Tab)var4.next();
                this.addTab(var5, var3++, true);
            }

            this.rightControlButtons = JFXTabPaneSkin.this.new TabControlButtons(JFXTabPaneSkin.ArrowPosition.RIGHT);
            this.leftControlButtons = JFXTabPaneSkin.this.new TabControlButtons(JFXTabPaneSkin.ArrowPosition.LEFT);
            this.rightControlButtons.setVisible(false);
            this.leftControlButtons.setVisible(false);
            this.rightControlButtons.inner.prefHeightProperty().bind(this.headersRegion.heightProperty());
            this.leftControlButtons.inner.prefHeightProperty().bind(this.headersRegion.heightProperty());
            this.getChildren().addAll(new Node[]{this.headerBackground, this.headersRegion, this.leftControlButtons, this.rightControlButtons});
            this.addEventHandler(ScrollEvent.SCROLL, (var1) -> {
                Side v2 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
                v2 = v2 == null?Side.TOP:v2;
                /*switch(null.$SwitchMap$javafx$geometry$Side[v2.ordinal()]) {
                    case 1:
                    case 2:
                    default:
                        this.setScrollOffset(this.scrollOffset - var1.getDeltaY());
                        break;
                    case 3:
                    case 4:
                        this.setScrollOffset(this.scrollOffset + var1.getDeltaY());
                }*/

            });
        }

        private void updateHeaderClip() {
            Side var1 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
            double var2 = 0.0D;
            double var4 = 0.0D;
            double var6 = 0.0D;
            double var8 = 0.0D;
            double var10 = 0.0D;
            double var12 = 0.0D;
            double var14 = this.firstTabIndent();
            double var16 = 2.0D * this.snapSize(this.rightControlButtons.prefWidth(-1.0D));
            this.measureClosingTabs = true;
            double var18 = this.snapSize(this.headersRegion.prefWidth(-1.0D));
            this.measureClosingTabs = false;
            double var20 = this.snapSize(this.headersRegion.prefHeight(-1.0D));
            if(var16 > 0.0D) {
                var16 += 10.0D;
            }

            if(this.headersRegion.getEffect() instanceof DropShadow) {
                DropShadow var22 = (DropShadow)this.headersRegion.getEffect();
                var12 = var22.getRadius();
            }

            var10 = this.snapSize(this.getWidth()) - var16 - var14;
            if(!var1.equals(Side.LEFT) && !var1.equals(Side.BOTTOM)) {
                var2 = -var12;
                var6 = (var18 < var10?var18:var10) + var12;
                var8 = var20;
            } else {
                if(var18 < var10) {
                    var6 = var18 + var12;
                } else {
                    var2 = var18 - var10;
                    var6 = var10 + var12;
                }

                var8 = var20;
            }

            this.headerClip.setX(var2);
            this.headerClip.setY(var4);
            this.headerClip.setWidth(var6 + 10.0D);
            this.headerClip.setHeight(var8);
        }

        private void addTab(Tab var1, int var2, boolean var3) {
            JFXTabPaneSkin.TabHeaderSkin var4 = JFXTabPaneSkin.this.new TabHeaderSkin(var1);
            var4.setVisible(var3);
            this.headersRegion.getChildren().add(var2, var4);
        }

        private void removeTab(Tab var1) {
            JFXTabPaneSkin.TabHeaderSkin var2 = this.getTabHeaderSkin(var1);
            if(var2 != null) {
                if(this.tabsFit()) {
                    this.headersRegion.getChildren().remove(var2);
                } else {
                    this.removeTab.add(var2);
                    var2.removeListeners(var1);
                }
            }

        }

        private JFXTabPaneSkin.TabHeaderSkin getTabHeaderSkin(Tab var1) {
            Iterator var2 = this.headersRegion.getChildren().iterator();

            while(var2.hasNext()) {
                Node var3 = (Node)var2.next();
                if(var3 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                    JFXTabPaneSkin.TabHeaderSkin var4 = (JFXTabPaneSkin.TabHeaderSkin)var3;
                    if(var4.getTab().equals(var1)) {
                        return var4;
                    }
                }
            }

            return null;
        }

        private boolean tabsFit() {
            return true;
            /*double var1 = this.snapSize(this.headersRegion.prefWidth(-1.0D));
            double var3 = 2.0D * this.snapSize(this.rightControlButtons.prefWidth(-1.0D));
            double var5 = var1 + var3 + this.firstTabIndent() + 10.0D;
            return var5 < this.getWidth();*/
        }

        private void ensureSelectedTabIsVisible() {
            double var1 = 0.0D;
            double var3 = 0.0D;
            double var5 = 0.0D;
            Iterator var7 = this.headersRegion.getChildren().iterator();

            while(var7.hasNext()) {
                Node var8 = (Node)var7.next();
                if(var8 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                    JFXTabPaneSkin.TabHeaderSkin var9 = (JFXTabPaneSkin.TabHeaderSkin)var8;
                    double var10 = this.snapSize(var9.prefWidth(-1.0D));
                    if(JFXTabPaneSkin.this.selectedTab != null && JFXTabPaneSkin.this.selectedTab.equals(var9.getTab())) {
                        var3 = var1;
                        var5 = var10;
                    }

                    var1 += var10;
                }
            }

            this.runTimeline(var3 + 1.0D, var5 - 2.0D);
        }

        private void runTimeline(double var1, double var3) {
            double var5 = this.selectedTabLine.getEndX();
            double var7 = this.selectedTabLineOffset;
            this.selectedTabLineOffset = var1;
            double var9 = var1 - var7;
            Timeline var11;
            if(var9 > 0.0D) {
                var11 = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(var5), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var7 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.seconds(0.15D), new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(var9), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var7 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.seconds(0.33D), new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(var3), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var1 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)})});
            } else {
                var11 = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(var5), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var7 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.seconds(0.15D), new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(-var9), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var1 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)}), new KeyFrame(Duration.seconds(0.33D), new KeyValue[]{new KeyValue(this.selectedTabLine.endXProperty(), Double.valueOf(var3), Interpolator.EASE_BOTH), new KeyValue(this.selectedTabLine.translateXProperty(), Double.valueOf(var1 + JFXTabPaneSkin.this.offsetStart), Interpolator.EASE_BOTH)})});
            }

            var11.play();
        }

        public double getScrollOffset() {
            return this.scrollOffset;
        }

        private void validateScrollOffset() {
            this.setScrollOffset(this.getScrollOffset());
        }

        public void setScrollOffset(double var1) {
            double var3 = this.snapSize(((TabPane)JFXTabPaneSkin.this.getSkinnable()).getWidth());
            double var5 = 2.0D * this.snapSize(this.rightControlButtons.getWidth());
            double var7 = var3 - var5 - this.firstTabIndent() - 10.0D;
            double var9 = 0.0D;
            Iterator var11 = this.headersRegion.getChildren().iterator();

            while(var11.hasNext()) {
                Node var12 = (Node)var11.next();
                if(var12 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                    JFXTabPaneSkin.TabHeaderSkin var13 = (JFXTabPaneSkin.TabHeaderSkin)var12;
                    double var14 = this.snapSize(var13.prefWidth(-1.0D));
                    var9 += var14;
                }
            }

            double var16;
            if(var7 - var1 > var9 && var1 < 0.0D) {
                var16 = var7 - var9;
            } else if(var1 > 0.0D) {
                var16 = 0.0D;
            } else {
                var16 = var1;
            }

            if(var16 != this.scrollOffset) {
                this.scrollOffset = var16;
                this.headersRegion.requestLayout();
                this.selectedTabLine.setTranslateX(this.selectedTabLineOffset + this.scrollOffset);
            }

        }

        private double firstTabIndent() {
            /*switch(null.SwitchMap.javafx.geometry.Side[((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide().ordinal()]) {
                case 1:
                case 2:
                    return this.snappedLeftInset();
                case 3:
                case 4:
                    return this.snappedTopInset();
                default:
                    return 0.0D;
            }*/
            return 0;
        }

        protected double computePrefWidth(double var1) {
            double var3 = JFXTabPaneSkin.this.isHorizontal()?this.snappedLeftInset() + this.snappedRightInset():this.snappedTopInset() + this.snappedBottomInset();
            return this.snapSize(this.headersRegion.prefWidth(var1)) + 2.0D * this.rightControlButtons.prefWidth(var1) + this.firstTabIndent() + 10.0D + var3;
        }

        protected double computePrefHeight(double var1) {
            double var3 = JFXTabPaneSkin.this.isHorizontal()?this.snappedTopInset() + this.snappedBottomInset():this.snappedLeftInset() + this.snappedRightInset();
            return this.snapSize(this.headersRegion.prefHeight(-1.0D)) + var3;
        }

        public double getBaselineOffset() {
            return ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide() == Side.TOP?this.headersRegion.getBaselineOffset() + this.snappedTopInset():0.0D;
        }

        protected void layoutChildren() {
            double var1 = this.snappedLeftInset();
            double var3 = this.snappedRightInset();
            double var5 = this.snappedTopInset();
            double var7 = this.snappedBottomInset();
            double var9 = this.snapSize(this.getWidth()) - (JFXTabPaneSkin.this.isHorizontal()?var1 + var3:var5 + var7);
            double var11 = this.snapSize(this.getHeight()) - (JFXTabPaneSkin.this.isHorizontal()?var5 + var7:var1 + var3);
            double var13 = this.snapSize(this.prefHeight(-1.0D));
            double var15 = this.snapSize(this.headersRegion.prefWidth(-1.0D));
            double var17 = this.snapSize(this.headersRegion.prefHeight(-1.0D));
            this.rightControlButtons.showTabsMenu(!this.tabsFit());
            this.leftControlButtons.showTabsMenu(!this.tabsFit());
            this.updateHeaderClip();
            this.headersRegion.requestLayout();
            double var19 = this.snapSize(this.rightControlButtons.prefWidth(-1.0D));
            double var21 = this.rightControlButtons.prefHeight(var19);
            this.rightControlButtons.resize(var19, var21);
            this.leftControlButtons.resize(var19, var21);
            this.headersRegion.resize(var15, var17);
            if(JFXTabPaneSkin.this.isFloatingStyleClass()) {
                this.headerBackground.setVisible(false);
            } else {
                this.headerBackground.resize(this.snapSize(this.getWidth()), this.snapSize(this.getHeight()));
                this.headerBackground.setVisible(true);
            }

            double var23 = 0.0D;
            double var25 = 0.0D;
            double var27 = 0.0D;
            double var29 = 0.0D;
            Side var31 = ((TabPane)JFXTabPaneSkin.this.getSkinnable()).getSide();
            if(var31.equals(Side.TOP)) {
                var23 = var1;
                var25 = var13 - var17 - var7;
                var27 = var9 - var19 + var1;
                var29 = this.snapSize(this.getHeight()) - var21 - var7;
            } else if(var31.equals(Side.RIGHT)) {
                var23 = var5;
                var25 = var13 - var17 - var1;
                var27 = var9 - var19 + var5;
                var29 = this.snapSize(this.getHeight()) - var21 - var1;
            } else if(var31.equals(Side.BOTTOM)) {
                var23 = this.snapSize(this.getWidth()) - var15 - var1;
                var25 = var13 - var17 - var5;
                var27 = var3;
                var29 = this.snapSize(this.getHeight()) - var21 - var5;
            } else if(var31.equals(Side.LEFT)) {
                var23 = this.snapSize(this.getWidth()) - var15 - var5;
                var25 = var13 - var17 - var3;
                var27 = var1;
                var29 = this.snapSize(this.getHeight()) - var21 - var3;
            }

            if(this.headerBackground.isVisible()) {
                this.positionInArea(this.headerBackground, 0.0D, 0.0D, this.snapSize(this.getWidth()), this.snapSize(this.getHeight()), 0.0D, HPos.CENTER, VPos.CENTER);
            }

            this.positionInArea(this.headersRegion, var23 + var19, var25, var9, var11, 0.0D, HPos.LEFT, VPos.CENTER);
            this.positionInArea(this.rightControlButtons, var27, var29, var19, var21, 0.0D, HPos.CENTER, VPos.CENTER);
            this.positionInArea(this.leftControlButtons, 0.0D, var29, var19, var21, 0.0D, HPos.CENTER, VPos.CENTER);
            if(!this.initialized) {
                double var32 = 0.0D;
                double var34 = 0.0D;
                double var36 = 0.0D;
                Iterator var38 = this.headersRegion.getChildren().iterator();

                while(var38.hasNext()) {
                    Node var39 = (Node)var38.next();
                    if(var39 instanceof JFXTabPaneSkin.TabHeaderSkin) {
                        JFXTabPaneSkin.TabHeaderSkin var40 = (JFXTabPaneSkin.TabHeaderSkin)var39;
                        double var41 = this.snapSize(var40.prefWidth(-1.0D));
                        if(JFXTabPaneSkin.this.selectedTab != null && JFXTabPaneSkin.this.selectedTab.equals(var40.getTab())) {
                            var34 = var32;
                            var36 = var41;
                        }

                        var32 += var41;
                    }
                }

                this.runTimeline(var34 + 1.0D, var36 - 2.0D);
                this.initialized = true;
            }

        }
    }

    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        private static final CssMetaData<TabPane, JFXTabPaneSkin.TabAnimation> OPEN_TAB_ANIMATION;
        private static final CssMetaData<TabPane, JFXTabPaneSkin.TabAnimation> CLOSE_TAB_ANIMATION;

        private StyleableProperties() {
        }

        static {
            OPEN_TAB_ANIMATION = new CssMetaData("-fx-open-tab-animation", new EnumConverter(JFXTabPaneSkin.TabAnimation.class), JFXTabPaneSkin.TabAnimation.GROW) {
                @Override
                public boolean isSettable(Styleable styleable) {
                    return false;
                }

                @Override
                public StyleableProperty getStyleableProperty(Styleable styleable) {
                    return null;
                }

                public boolean isSettable(TabPane var1) {
                    return true;
                }

                public StyleableProperty<TabAnimation> getStyleableProperty(TabPane var1) {
                    JFXTabPaneSkin var2 = (JFXTabPaneSkin)var1.getSkin();
                    return (StyleableProperty)var2.openTabAnimation;
                }
            };
            CLOSE_TAB_ANIMATION = new CssMetaData("-fx-close-tab-animation", new EnumConverter(JFXTabPaneSkin.TabAnimation.class), JFXTabPaneSkin.TabAnimation.GROW) {
                @Override
                public boolean isSettable(Styleable styleable) {
                    return false;
                }

                @Override
                public StyleableProperty getStyleableProperty(Styleable styleable) {
                    return null;
                }

                public boolean isSettable(TabPane var1) {
                    return true;
                }

                public StyleableProperty<JFXTabPaneSkin.TabAnimation> getStyleableProperty(TabPane var1) {
                    JFXTabPaneSkin var2 = (JFXTabPaneSkin)var1.getSkin();
                    return (StyleableProperty)var2.closeTabAnimation;
                }
            };
            ArrayList var0 = new ArrayList(SkinBase.getClassCssMetaData());
            var0.add(OPEN_TAB_ANIMATION);
            var0.add(CLOSE_TAB_ANIMATION);
            STYLEABLES = Collections.unmodifiableList(var0);
        }
    }

    private static enum TabAnimation {
        NONE,
        GROW;

        private TabAnimation() {
        }
    }
}

