package gama.core.lang.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import gama.core.lang.services.GamlGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all")
public class InternalGamlParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_STRING", "RULE_INTEGER", "RULE_DOUBLE", "RULE_BOOLEAN", "RULE_KEYWORD", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'__synthetic__'", "'<-'", "'model'", "'import'", "'as'", "'@'", "'model:'", "';'", "'global'", "'loop'", "'if'", "'else'", "'try'", "'catch'", "'return'", "'('", "')'", "'action'", "'{'", "'}'", "'='", "'display'", "'equation'", "'solve'", "'species'", "'grid'", "'experiment'", "'ask'", "'release'", "'capture'", "'create'", "'write'", "'error'", "'warn'", "'exception'", "'save'", "'assert'", "'inspect'", "'browse'", "'draw'", "'using'", "'switch'", "'put'", "'add'", "'remove'", "'match'", "'match_between'", "'match_one'", "'parameter'", "'status'", "'highlight'", "'focus_on'", "'layout'", "'light'", "'camera'", "'image'", "'data'", "'chart'", "'agents'", "'graphics'", "'event'", "'overlay'", "'datalist'", "'do'", "'invoke'", "'init'", "'reflex'", "'aspect'", "'<<'", "'>'", "'<<+'", "'>-'", "'+<-'", "'<+'", "','", "':'", "'returns:'", "'data:'", "'init:'", "'layout:'", "'image:'", "'parameter:'", "'aspect:'", "'light:'", "'as:'", "'species:'", "'action:'", "'on_change:'", "'->'", "'::'", "'?'", "'or'", "'and'", "'!='", "'>='", "'<='", "'<'", "'+'", "'-'", "'*'", "'/'", "'^'", "'#'", "'!'", "'not'", "'['", "']'", "'.'", "'**unit*'", "'**type*'", "'**action*'", "'**skill*'", "'**var*'", "'**equation*'"
    };
    public static final int T__50=50;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int RULE_ID=4;
    public static final int T__66=66;
    public static final int RULE_ML_COMMENT=10;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__126=126;
    public static final int T__63=63;
    public static final int T__125=125;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__127=127;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_KEYWORD=9;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int RULE_BOOLEAN=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__99=99;
    public static final int T__14=14;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__122=122;
    public static final int T__70=70;
    public static final int T__121=121;
    public static final int T__71=71;
    public static final int T__124=124;
    public static final int T__72=72;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int RULE_STRING=5;
    public static final int RULE_SL_COMMENT=11;
    public static final int RULE_DOUBLE=7;
    public static final int T__77=77;
    public static final int T__119=119;
    public static final int T__78=78;
    public static final int T__118=118;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int EOF=-1;
    public static final int T__74=74;
    public static final int T__114=114;
    public static final int T__75=75;
    public static final int T__117=117;
    public static final int T__76=76;
    public static final int T__116=116;
    public static final int T__80=80;
    public static final int T__111=111;
    public static final int T__81=81;
    public static final int T__110=110;
    public static final int T__82=82;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int RULE_WS=12;
    public static final int RULE_ANY_OTHER=13;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int T__109=109;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int RULE_INTEGER=6;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    // delegators


        public InternalGamlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalGamlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalGamlParser.tokenNames; }
    public String getGrammarFileName() { return "InternalGaml.g"; }



     	private GamlGrammarAccess grammarAccess;

        public InternalGamlParser(TokenStream input, GamlGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Entry";
       	}

       	@Override
       	protected GamlGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleEntry"
    // InternalGaml.g:64:1: entryRuleEntry returns [EObject current=null] : iv_ruleEntry= ruleEntry EOF ;
    public final EObject entryRuleEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntry = null;


        try {
            // InternalGaml.g:64:46: (iv_ruleEntry= ruleEntry EOF )
            // InternalGaml.g:65:2: iv_ruleEntry= ruleEntry EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEntryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEntry=ruleEntry();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEntry; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntry"


    // $ANTLR start "ruleEntry"
    // InternalGaml.g:71:1: ruleEntry returns [EObject current=null] : ( ( ( '@' | 'model' )=>this_Model_0= ruleModel ) | this_StringEvaluator_1= ruleStringEvaluator | this_StandaloneBlock_2= ruleStandaloneBlock | this_ExperimentFileStructure_3= ruleExperimentFileStructure ) ;
    public final EObject ruleEntry() throws RecognitionException {
        EObject current = null;

        EObject this_Model_0 = null;

        EObject this_StringEvaluator_1 = null;

        EObject this_StandaloneBlock_2 = null;

        EObject this_ExperimentFileStructure_3 = null;



        	enterRule();

        try {
            // InternalGaml.g:77:2: ( ( ( ( '@' | 'model' )=>this_Model_0= ruleModel ) | this_StringEvaluator_1= ruleStringEvaluator | this_StandaloneBlock_2= ruleStandaloneBlock | this_ExperimentFileStructure_3= ruleExperimentFileStructure ) )
            // InternalGaml.g:78:2: ( ( ( '@' | 'model' )=>this_Model_0= ruleModel ) | this_StringEvaluator_1= ruleStringEvaluator | this_StandaloneBlock_2= ruleStandaloneBlock | this_ExperimentFileStructure_3= ruleExperimentFileStructure )
            {
            // InternalGaml.g:78:2: ( ( ( '@' | 'model' )=>this_Model_0= ruleModel ) | this_StringEvaluator_1= ruleStringEvaluator | this_StandaloneBlock_2= ruleStandaloneBlock | this_ExperimentFileStructure_3= ruleExperimentFileStructure )
            int alt1=4;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==19) && (synpred1_InternalGaml())) {
                alt1=1;
            }
            else if ( (LA1_0==16) && (synpred1_InternalGaml())) {
                alt1=1;
            }
            else if ( (LA1_0==RULE_ID) ) {
                alt1=2;
            }
            else if ( (LA1_0==14) ) {
                alt1=3;
            }
            else if ( (LA1_0==40) ) {
                alt1=4;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // InternalGaml.g:79:3: ( ( '@' | 'model' )=>this_Model_0= ruleModel )
                    {
                    // InternalGaml.g:79:3: ( ( '@' | 'model' )=>this_Model_0= ruleModel )
                    // InternalGaml.g:80:4: ( '@' | 'model' )=>this_Model_0= ruleModel
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getEntryAccess().getModelParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Model_0=ruleModel();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Model_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:91:3: this_StringEvaluator_1= ruleStringEvaluator
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getStringEvaluatorParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StringEvaluator_1=ruleStringEvaluator();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StringEvaluator_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:100:3: this_StandaloneBlock_2= ruleStandaloneBlock
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getStandaloneBlockParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StandaloneBlock_2=ruleStandaloneBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StandaloneBlock_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:109:3: this_ExperimentFileStructure_3= ruleExperimentFileStructure
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEntryAccess().getExperimentFileStructureParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ExperimentFileStructure_3=ruleExperimentFileStructure();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ExperimentFileStructure_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntry"


    // $ANTLR start "entryRuleStandaloneBlock"
    // InternalGaml.g:121:1: entryRuleStandaloneBlock returns [EObject current=null] : iv_ruleStandaloneBlock= ruleStandaloneBlock EOF ;
    public final EObject entryRuleStandaloneBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStandaloneBlock = null;


        try {
            // InternalGaml.g:121:56: (iv_ruleStandaloneBlock= ruleStandaloneBlock EOF )
            // InternalGaml.g:122:2: iv_ruleStandaloneBlock= ruleStandaloneBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStandaloneBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStandaloneBlock=ruleStandaloneBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStandaloneBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStandaloneBlock"


    // $ANTLR start "ruleStandaloneBlock"
    // InternalGaml.g:128:1: ruleStandaloneBlock returns [EObject current=null] : (otherlv_0= '__synthetic__' ( (lv_block_1_0= ruleBlock ) ) ) ;
    public final EObject ruleStandaloneBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        EObject lv_block_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:134:2: ( (otherlv_0= '__synthetic__' ( (lv_block_1_0= ruleBlock ) ) ) )
            // InternalGaml.g:135:2: (otherlv_0= '__synthetic__' ( (lv_block_1_0= ruleBlock ) ) )
            {
            // InternalGaml.g:135:2: (otherlv_0= '__synthetic__' ( (lv_block_1_0= ruleBlock ) ) )
            // InternalGaml.g:136:3: otherlv_0= '__synthetic__' ( (lv_block_1_0= ruleBlock ) )
            {
            otherlv_0=(Token)match(input,14,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getStandaloneBlockAccess().get__synthetic__Keyword_0());
              		
            }
            // InternalGaml.g:140:3: ( (lv_block_1_0= ruleBlock ) )
            // InternalGaml.g:141:4: (lv_block_1_0= ruleBlock )
            {
            // InternalGaml.g:141:4: (lv_block_1_0= ruleBlock )
            // InternalGaml.g:142:5: lv_block_1_0= ruleBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStandaloneBlockAccess().getBlockBlockParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_1_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStandaloneBlockRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_1_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStandaloneBlock"


    // $ANTLR start "entryRuleStringEvaluator"
    // InternalGaml.g:163:1: entryRuleStringEvaluator returns [EObject current=null] : iv_ruleStringEvaluator= ruleStringEvaluator EOF ;
    public final EObject entryRuleStringEvaluator() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringEvaluator = null;


        try {
            // InternalGaml.g:163:56: (iv_ruleStringEvaluator= ruleStringEvaluator EOF )
            // InternalGaml.g:164:2: iv_ruleStringEvaluator= ruleStringEvaluator EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringEvaluatorRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringEvaluator=ruleStringEvaluator();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringEvaluator; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringEvaluator"


    // $ANTLR start "ruleStringEvaluator"
    // InternalGaml.g:170:1: ruleStringEvaluator returns [EObject current=null] : ( ( (lv_toto_0_0= RULE_ID ) ) otherlv_1= '<-' ( (lv_expr_2_0= ruleExpression ) ) ) ;
    public final EObject ruleStringEvaluator() throws RecognitionException {
        EObject current = null;

        Token lv_toto_0_0=null;
        Token otherlv_1=null;
        EObject lv_expr_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:176:2: ( ( ( (lv_toto_0_0= RULE_ID ) ) otherlv_1= '<-' ( (lv_expr_2_0= ruleExpression ) ) ) )
            // InternalGaml.g:177:2: ( ( (lv_toto_0_0= RULE_ID ) ) otherlv_1= '<-' ( (lv_expr_2_0= ruleExpression ) ) )
            {
            // InternalGaml.g:177:2: ( ( (lv_toto_0_0= RULE_ID ) ) otherlv_1= '<-' ( (lv_expr_2_0= ruleExpression ) ) )
            // InternalGaml.g:178:3: ( (lv_toto_0_0= RULE_ID ) ) otherlv_1= '<-' ( (lv_expr_2_0= ruleExpression ) )
            {
            // InternalGaml.g:178:3: ( (lv_toto_0_0= RULE_ID ) )
            // InternalGaml.g:179:4: (lv_toto_0_0= RULE_ID )
            {
            // InternalGaml.g:179:4: (lv_toto_0_0= RULE_ID )
            // InternalGaml.g:180:5: lv_toto_0_0= RULE_ID
            {
            lv_toto_0_0=(Token)match(input,RULE_ID,FOLLOW_4); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_toto_0_0, grammarAccess.getStringEvaluatorAccess().getTotoIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getStringEvaluatorRule());
              					}
              					setWithLastConsumed(
              						current,
              						"toto",
              						lv_toto_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            otherlv_1=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getStringEvaluatorAccess().getLessThanSignHyphenMinusKeyword_1());
              		
            }
            // InternalGaml.g:200:3: ( (lv_expr_2_0= ruleExpression ) )
            // InternalGaml.g:201:4: (lv_expr_2_0= ruleExpression )
            {
            // InternalGaml.g:201:4: (lv_expr_2_0= ruleExpression )
            // InternalGaml.g:202:5: lv_expr_2_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getStringEvaluatorAccess().getExprExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expr_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getStringEvaluatorRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringEvaluator"


    // $ANTLR start "entryRuleModel"
    // InternalGaml.g:223:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalGaml.g:223:46: (iv_ruleModel= ruleModel EOF )
            // InternalGaml.g:224:2: iv_ruleModel= ruleModel EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModel; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalGaml.g:230:1: ruleModel returns [EObject current=null] : ( ( (lv_pragmas_0_0= rulePragma ) )* otherlv_1= 'model' ( (lv_name_2_0= RULE_ID ) ) ( (lv_imports_3_0= ruleImport ) )* ( (lv_block_4_0= ruleModelBlock ) ) ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_name_2_0=null;
        EObject lv_pragmas_0_0 = null;

        EObject lv_imports_3_0 = null;

        EObject lv_block_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:236:2: ( ( ( (lv_pragmas_0_0= rulePragma ) )* otherlv_1= 'model' ( (lv_name_2_0= RULE_ID ) ) ( (lv_imports_3_0= ruleImport ) )* ( (lv_block_4_0= ruleModelBlock ) ) ) )
            // InternalGaml.g:237:2: ( ( (lv_pragmas_0_0= rulePragma ) )* otherlv_1= 'model' ( (lv_name_2_0= RULE_ID ) ) ( (lv_imports_3_0= ruleImport ) )* ( (lv_block_4_0= ruleModelBlock ) ) )
            {
            // InternalGaml.g:237:2: ( ( (lv_pragmas_0_0= rulePragma ) )* otherlv_1= 'model' ( (lv_name_2_0= RULE_ID ) ) ( (lv_imports_3_0= ruleImport ) )* ( (lv_block_4_0= ruleModelBlock ) ) )
            // InternalGaml.g:238:3: ( (lv_pragmas_0_0= rulePragma ) )* otherlv_1= 'model' ( (lv_name_2_0= RULE_ID ) ) ( (lv_imports_3_0= ruleImport ) )* ( (lv_block_4_0= ruleModelBlock ) )
            {
            // InternalGaml.g:238:3: ( (lv_pragmas_0_0= rulePragma ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==19) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // InternalGaml.g:239:4: (lv_pragmas_0_0= rulePragma )
            	    {
            	    // InternalGaml.g:239:4: (lv_pragmas_0_0= rulePragma )
            	    // InternalGaml.g:240:5: lv_pragmas_0_0= rulePragma
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getPragmasPragmaParserRuleCall_0_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_6);
            	    lv_pragmas_0_0=rulePragma();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"pragmas",
            	      						lv_pragmas_0_0,
            	      						"gama.core.lang.Gaml.Pragma");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            otherlv_1=(Token)match(input,16,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getModelAccess().getModelKeyword_1());
              		
            }
            // InternalGaml.g:261:3: ( (lv_name_2_0= RULE_ID ) )
            // InternalGaml.g:262:4: (lv_name_2_0= RULE_ID )
            {
            // InternalGaml.g:262:4: (lv_name_2_0= RULE_ID )
            // InternalGaml.g:263:5: lv_name_2_0= RULE_ID
            {
            lv_name_2_0=(Token)match(input,RULE_ID,FOLLOW_8); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_2_0, grammarAccess.getModelAccess().getNameIDTerminalRuleCall_2_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getModelRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_2_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            // InternalGaml.g:279:3: ( (lv_imports_3_0= ruleImport ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==17) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // InternalGaml.g:280:4: (lv_imports_3_0= ruleImport )
            	    {
            	    // InternalGaml.g:280:4: (lv_imports_3_0= ruleImport )
            	    // InternalGaml.g:281:5: lv_imports_3_0= ruleImport
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelAccess().getImportsImportParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_8);
            	    lv_imports_3_0=ruleImport();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelRule());
            	      					}
            	      					add(
            	      						current,
            	      						"imports",
            	      						lv_imports_3_0,
            	      						"gama.core.lang.Gaml.Import");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // InternalGaml.g:298:3: ( (lv_block_4_0= ruleModelBlock ) )
            // InternalGaml.g:299:4: (lv_block_4_0= ruleModelBlock )
            {
            // InternalGaml.g:299:4: (lv_block_4_0= ruleModelBlock )
            // InternalGaml.g:300:5: lv_block_4_0= ruleModelBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getModelAccess().getBlockModelBlockParserRuleCall_4_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_4_0=ruleModelBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getModelRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_4_0,
              						"gama.core.lang.Gaml.ModelBlock");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleModelBlock"
    // InternalGaml.g:321:1: entryRuleModelBlock returns [EObject current=null] : iv_ruleModelBlock= ruleModelBlock EOF ;
    public final EObject entryRuleModelBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModelBlock = null;


        try {
            // InternalGaml.g:321:51: (iv_ruleModelBlock= ruleModelBlock EOF )
            // InternalGaml.g:322:2: iv_ruleModelBlock= ruleModelBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getModelBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleModelBlock=ruleModelBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleModelBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModelBlock"


    // $ANTLR start "ruleModelBlock"
    // InternalGaml.g:328:1: ruleModelBlock returns [EObject current=null] : ( () ( (lv_statements_1_0= ruleS_Section ) )* ) ;
    public final EObject ruleModelBlock() throws RecognitionException {
        EObject current = null;

        EObject lv_statements_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:334:2: ( ( () ( (lv_statements_1_0= ruleS_Section ) )* ) )
            // InternalGaml.g:335:2: ( () ( (lv_statements_1_0= ruleS_Section ) )* )
            {
            // InternalGaml.g:335:2: ( () ( (lv_statements_1_0= ruleS_Section ) )* )
            // InternalGaml.g:336:3: () ( (lv_statements_1_0= ruleS_Section ) )*
            {
            // InternalGaml.g:336:3: ()
            // InternalGaml.g:337:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getModelBlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:343:3: ( (lv_statements_1_0= ruleS_Section ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==22||(LA4_0>=38 && LA4_0<=40)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // InternalGaml.g:344:4: (lv_statements_1_0= ruleS_Section )
            	    {
            	    // InternalGaml.g:344:4: (lv_statements_1_0= ruleS_Section )
            	    // InternalGaml.g:345:5: lv_statements_1_0= ruleS_Section
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getModelBlockAccess().getStatementsS_SectionParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_9);
            	    lv_statements_1_0=ruleS_Section();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getModelBlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_1_0,
            	      						"gama.core.lang.Gaml.S_Section");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModelBlock"


    // $ANTLR start "entryRuleImport"
    // InternalGaml.g:366:1: entryRuleImport returns [EObject current=null] : iv_ruleImport= ruleImport EOF ;
    public final EObject entryRuleImport() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleImport = null;


        try {
            // InternalGaml.g:366:47: (iv_ruleImport= ruleImport EOF )
            // InternalGaml.g:367:2: iv_ruleImport= ruleImport EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getImportRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleImport=ruleImport();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleImport; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleImport"


    // $ANTLR start "ruleImport"
    // InternalGaml.g:373:1: ruleImport returns [EObject current=null] : (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )? ) ;
    public final EObject ruleImport() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_importURI_1_0=null;
        Token otherlv_2=null;
        AntlrDatatypeRuleToken lv_name_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:379:2: ( (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )? ) )
            // InternalGaml.g:380:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )? )
            {
            // InternalGaml.g:380:2: (otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )? )
            // InternalGaml.g:381:3: otherlv_0= 'import' ( (lv_importURI_1_0= RULE_STRING ) ) (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )?
            {
            otherlv_0=(Token)match(input,17,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getImportAccess().getImportKeyword_0());
              		
            }
            // InternalGaml.g:385:3: ( (lv_importURI_1_0= RULE_STRING ) )
            // InternalGaml.g:386:4: (lv_importURI_1_0= RULE_STRING )
            {
            // InternalGaml.g:386:4: (lv_importURI_1_0= RULE_STRING )
            // InternalGaml.g:387:5: lv_importURI_1_0= RULE_STRING
            {
            lv_importURI_1_0=(Token)match(input,RULE_STRING,FOLLOW_11); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_importURI_1_0, grammarAccess.getImportAccess().getImportURISTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getImportRule());
              					}
              					setWithLastConsumed(
              						current,
              						"importURI",
              						lv_importURI_1_0,
              						"gama.core.lang.Gaml.STRING");
              				
            }

            }


            }

            // InternalGaml.g:403:3: (otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==18) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalGaml.g:404:4: otherlv_2= 'as' ( (lv_name_3_0= ruleValid_ID ) )
                    {
                    otherlv_2=(Token)match(input,18,FOLLOW_12); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getImportAccess().getAsKeyword_2_0());
                      			
                    }
                    // InternalGaml.g:408:4: ( (lv_name_3_0= ruleValid_ID ) )
                    // InternalGaml.g:409:5: (lv_name_3_0= ruleValid_ID )
                    {
                    // InternalGaml.g:409:5: (lv_name_3_0= ruleValid_ID )
                    // InternalGaml.g:410:6: lv_name_3_0= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getImportAccess().getNameValid_IDParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_3_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getImportRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_3_0,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleImport"


    // $ANTLR start "entryRulePragma"
    // InternalGaml.g:432:1: entryRulePragma returns [EObject current=null] : iv_rulePragma= rulePragma EOF ;
    public final EObject entryRulePragma() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePragma = null;


        try {
            // InternalGaml.g:432:47: (iv_rulePragma= rulePragma EOF )
            // InternalGaml.g:433:2: iv_rulePragma= rulePragma EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPragmaRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePragma=rulePragma();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePragma; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePragma"


    // $ANTLR start "rulePragma"
    // InternalGaml.g:439:1: rulePragma returns [EObject current=null] : (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject rulePragma() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            // InternalGaml.g:445:2: ( (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) ) )
            // InternalGaml.g:446:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) )
            {
            // InternalGaml.g:446:2: (otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) ) )
            // InternalGaml.g:447:3: otherlv_0= '@' ( (lv_name_1_0= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,19,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getPragmaAccess().getCommercialAtKeyword_0());
              		
            }
            // InternalGaml.g:451:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGaml.g:452:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGaml.g:452:4: (lv_name_1_0= RULE_ID )
            // InternalGaml.g:453:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getPragmaAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getPragmaRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePragma"


    // $ANTLR start "entryRuleExperimentFileStructure"
    // InternalGaml.g:473:1: entryRuleExperimentFileStructure returns [EObject current=null] : iv_ruleExperimentFileStructure= ruleExperimentFileStructure EOF ;
    public final EObject entryRuleExperimentFileStructure() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExperimentFileStructure = null;


        try {
            // InternalGaml.g:473:64: (iv_ruleExperimentFileStructure= ruleExperimentFileStructure EOF )
            // InternalGaml.g:474:2: iv_ruleExperimentFileStructure= ruleExperimentFileStructure EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExperimentFileStructureRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExperimentFileStructure=ruleExperimentFileStructure();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExperimentFileStructure; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExperimentFileStructure"


    // $ANTLR start "ruleExperimentFileStructure"
    // InternalGaml.g:480:1: ruleExperimentFileStructure returns [EObject current=null] : ( (lv_exp_0_0= ruleHeadlessExperiment ) ) ;
    public final EObject ruleExperimentFileStructure() throws RecognitionException {
        EObject current = null;

        EObject lv_exp_0_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:486:2: ( ( (lv_exp_0_0= ruleHeadlessExperiment ) ) )
            // InternalGaml.g:487:2: ( (lv_exp_0_0= ruleHeadlessExperiment ) )
            {
            // InternalGaml.g:487:2: ( (lv_exp_0_0= ruleHeadlessExperiment ) )
            // InternalGaml.g:488:3: (lv_exp_0_0= ruleHeadlessExperiment )
            {
            // InternalGaml.g:488:3: (lv_exp_0_0= ruleHeadlessExperiment )
            // InternalGaml.g:489:4: lv_exp_0_0= ruleHeadlessExperiment
            {
            if ( state.backtracking==0 ) {

              				newCompositeNode(grammarAccess.getExperimentFileStructureAccess().getExpHeadlessExperimentParserRuleCall_0());
              			
            }
            pushFollow(FOLLOW_2);
            lv_exp_0_0=ruleHeadlessExperiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElementForParent(grammarAccess.getExperimentFileStructureRule());
              				}
              				set(
              					current,
              					"exp",
              					lv_exp_0_0,
              					"gama.core.lang.Gaml.HeadlessExperiment");
              				afterParserOrEnumRuleCall();
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExperimentFileStructure"


    // $ANTLR start "entryRuleHeadlessExperiment"
    // InternalGaml.g:509:1: entryRuleHeadlessExperiment returns [EObject current=null] : iv_ruleHeadlessExperiment= ruleHeadlessExperiment EOF ;
    public final EObject entryRuleHeadlessExperiment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleHeadlessExperiment = null;


        try {
            // InternalGaml.g:509:59: (iv_ruleHeadlessExperiment= ruleHeadlessExperiment EOF )
            // InternalGaml.g:510:2: iv_ruleHeadlessExperiment= ruleHeadlessExperiment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getHeadlessExperimentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleHeadlessExperiment=ruleHeadlessExperiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleHeadlessExperiment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleHeadlessExperiment"


    // $ANTLR start "ruleHeadlessExperiment"
    // InternalGaml.g:516:1: ruleHeadlessExperiment returns [EObject current=null] : ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )? ( (lv_facets_4_0= ruleFacet ) )* ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' ) ) ;
    public final EObject ruleHeadlessExperiment() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_2=null;
        Token otherlv_2=null;
        Token lv_importURI_3_0=null;
        Token otherlv_6=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_4_0 = null;

        EObject lv_block_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:522:2: ( ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )? ( (lv_facets_4_0= ruleFacet ) )* ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' ) ) )
            // InternalGaml.g:523:2: ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )? ( (lv_facets_4_0= ruleFacet ) )* ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' ) )
            {
            // InternalGaml.g:523:2: ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )? ( (lv_facets_4_0= ruleFacet ) )* ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' ) )
            // InternalGaml.g:524:3: ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )? ( (lv_facets_4_0= ruleFacet ) )* ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' )
            {
            // InternalGaml.g:524:3: ( (lv_key_0_0= rule_ExperimentKey ) )
            // InternalGaml.g:525:4: (lv_key_0_0= rule_ExperimentKey )
            {
            // InternalGaml.g:525:4: (lv_key_0_0= rule_ExperimentKey )
            // InternalGaml.g:526:5: lv_key_0_0= rule_ExperimentKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getKey_ExperimentKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ExperimentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:543:3: ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) )
            // InternalGaml.g:544:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            {
            // InternalGaml.g:544:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            // InternalGaml.g:545:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            {
            // InternalGaml.g:545:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==RULE_ID||LA6_0==36||(LA6_0>=38 && LA6_0<=81)) ) {
                alt6=1;
            }
            else if ( (LA6_0==RULE_STRING) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // InternalGaml.g:546:6: lv_name_1_1= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_14);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:562:6: lv_name_1_2= RULE_STRING
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_14); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getHeadlessExperimentAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalGaml.g:579:3: (otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==20) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalGaml.g:580:4: otherlv_2= 'model:' ( (lv_importURI_3_0= RULE_STRING ) )
                    {
                    otherlv_2=(Token)match(input,20,FOLLOW_10); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getHeadlessExperimentAccess().getModelKeyword_2_0());
                      			
                    }
                    // InternalGaml.g:584:4: ( (lv_importURI_3_0= RULE_STRING ) )
                    // InternalGaml.g:585:5: (lv_importURI_3_0= RULE_STRING )
                    {
                    // InternalGaml.g:585:5: (lv_importURI_3_0= RULE_STRING )
                    // InternalGaml.g:586:6: lv_importURI_3_0= RULE_STRING
                    {
                    lv_importURI_3_0=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_importURI_3_0, grammarAccess.getHeadlessExperimentAccess().getImportURISTRINGTerminalRuleCall_2_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"importURI",
                      							lv_importURI_3_0,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalGaml.g:603:3: ( (lv_facets_4_0= ruleFacet ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==RULE_ID||LA8_0==15||(LA8_0>=90 && LA8_0<=102)) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // InternalGaml.g:604:4: (lv_facets_4_0= ruleFacet )
            	    {
            	    // InternalGaml.g:604:4: (lv_facets_4_0= ruleFacet )
            	    // InternalGaml.g:605:5: lv_facets_4_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_4_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_4_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            // InternalGaml.g:622:3: ( ( (lv_block_5_0= ruleBlock ) ) | otherlv_6= ';' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==32) ) {
                alt9=1;
            }
            else if ( (LA9_0==21) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // InternalGaml.g:623:4: ( (lv_block_5_0= ruleBlock ) )
                    {
                    // InternalGaml.g:623:4: ( (lv_block_5_0= ruleBlock ) )
                    // InternalGaml.g:624:5: (lv_block_5_0= ruleBlock )
                    {
                    // InternalGaml.g:624:5: (lv_block_5_0= ruleBlock )
                    // InternalGaml.g:625:6: lv_block_5_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getHeadlessExperimentAccess().getBlockBlockParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_5_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getHeadlessExperimentRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_5_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:643:4: otherlv_6= ';'
                    {
                    otherlv_6=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_6, grammarAccess.getHeadlessExperimentAccess().getSemicolonKeyword_4_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleHeadlessExperiment"


    // $ANTLR start "entryRuleS_Section"
    // InternalGaml.g:652:1: entryRuleS_Section returns [EObject current=null] : iv_ruleS_Section= ruleS_Section EOF ;
    public final EObject entryRuleS_Section() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Section = null;


        try {
            // InternalGaml.g:652:50: (iv_ruleS_Section= ruleS_Section EOF )
            // InternalGaml.g:653:2: iv_ruleS_Section= ruleS_Section EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SectionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Section=ruleS_Section();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Section; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Section"


    // $ANTLR start "ruleS_Section"
    // InternalGaml.g:659:1: ruleS_Section returns [EObject current=null] : (this_S_Global_0= ruleS_Global | this_S_Species_1= ruleS_Species | this_S_Experiment_2= ruleS_Experiment ) ;
    public final EObject ruleS_Section() throws RecognitionException {
        EObject current = null;

        EObject this_S_Global_0 = null;

        EObject this_S_Species_1 = null;

        EObject this_S_Experiment_2 = null;



        	enterRule();

        try {
            // InternalGaml.g:665:2: ( (this_S_Global_0= ruleS_Global | this_S_Species_1= ruleS_Species | this_S_Experiment_2= ruleS_Experiment ) )
            // InternalGaml.g:666:2: (this_S_Global_0= ruleS_Global | this_S_Species_1= ruleS_Species | this_S_Experiment_2= ruleS_Experiment )
            {
            // InternalGaml.g:666:2: (this_S_Global_0= ruleS_Global | this_S_Species_1= ruleS_Species | this_S_Experiment_2= ruleS_Experiment )
            int alt10=3;
            switch ( input.LA(1) ) {
            case 22:
                {
                alt10=1;
                }
                break;
            case 38:
            case 39:
                {
                alt10=2;
                }
                break;
            case 40:
                {
                alt10=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalGaml.g:667:3: this_S_Global_0= ruleS_Global
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_GlobalParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Global_0=ruleS_Global();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Global_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:676:3: this_S_Species_1= ruleS_Species
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_SpeciesParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Species_1=ruleS_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Species_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:685:3: this_S_Experiment_2= ruleS_Experiment
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_SectionAccess().getS_ExperimentParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Experiment_2=ruleS_Experiment();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Experiment_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Section"


    // $ANTLR start "entryRuleS_Global"
    // InternalGaml.g:697:1: entryRuleS_Global returns [EObject current=null] : iv_ruleS_Global= ruleS_Global EOF ;
    public final EObject entryRuleS_Global() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Global = null;


        try {
            // InternalGaml.g:697:49: (iv_ruleS_Global= ruleS_Global EOF )
            // InternalGaml.g:698:2: iv_ruleS_Global= ruleS_Global EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_GlobalRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Global=ruleS_Global();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Global; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Global"


    // $ANTLR start "ruleS_Global"
    // InternalGaml.g:704:1: ruleS_Global returns [EObject current=null] : ( ( (lv_key_0_0= 'global' ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) ) ;
    public final EObject ruleS_Global() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_facets_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:710:2: ( ( ( (lv_key_0_0= 'global' ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) ) )
            // InternalGaml.g:711:2: ( ( (lv_key_0_0= 'global' ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) )
            {
            // InternalGaml.g:711:2: ( ( (lv_key_0_0= 'global' ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) )
            // InternalGaml.g:712:3: ( (lv_key_0_0= 'global' ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' )
            {
            // InternalGaml.g:712:3: ( (lv_key_0_0= 'global' ) )
            // InternalGaml.g:713:4: (lv_key_0_0= 'global' )
            {
            // InternalGaml.g:713:4: (lv_key_0_0= 'global' )
            // InternalGaml.g:714:5: lv_key_0_0= 'global'
            {
            lv_key_0_0=(Token)match(input,22,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_GlobalAccess().getKeyGlobalKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_GlobalRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "global");
              				
            }

            }


            }

            // InternalGaml.g:726:3: ( (lv_facets_1_0= ruleFacet ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==RULE_ID||LA11_0==15||(LA11_0>=90 && LA11_0<=102)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalGaml.g:727:4: (lv_facets_1_0= ruleFacet )
            	    {
            	    // InternalGaml.g:727:4: (lv_facets_1_0= ruleFacet )
            	    // InternalGaml.g:728:5: lv_facets_1_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_GlobalAccess().getFacetsFacetParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_1_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_GlobalRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_1_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // InternalGaml.g:745:3: ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==32) ) {
                alt12=1;
            }
            else if ( (LA12_0==21) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // InternalGaml.g:746:4: ( (lv_block_2_0= ruleBlock ) )
                    {
                    // InternalGaml.g:746:4: ( (lv_block_2_0= ruleBlock ) )
                    // InternalGaml.g:747:5: (lv_block_2_0= ruleBlock )
                    {
                    // InternalGaml.g:747:5: (lv_block_2_0= ruleBlock )
                    // InternalGaml.g:748:6: lv_block_2_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_GlobalAccess().getBlockBlockParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_GlobalRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:766:4: otherlv_3= ';'
                    {
                    otherlv_3=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_GlobalAccess().getSemicolonKeyword_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Global"


    // $ANTLR start "entryRuleS_Species"
    // InternalGaml.g:775:1: entryRuleS_Species returns [EObject current=null] : iv_ruleS_Species= ruleS_Species EOF ;
    public final EObject entryRuleS_Species() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Species = null;


        try {
            // InternalGaml.g:775:50: (iv_ruleS_Species= ruleS_Species EOF )
            // InternalGaml.g:776:2: iv_ruleS_Species= ruleS_Species EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SpeciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Species=ruleS_Species();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Species; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Species"


    // $ANTLR start "ruleS_Species"
    // InternalGaml.g:782:1: ruleS_Species returns [EObject current=null] : ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_name_1_0= RULE_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_Species() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_0=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:788:2: ( ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_name_1_0= RULE_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:789:2: ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_name_1_0= RULE_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:789:2: ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_name_1_0= RULE_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:790:3: ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_name_1_0= RULE_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:790:3: ( (lv_key_0_0= rule_SpeciesKey ) )
            // InternalGaml.g:791:4: (lv_key_0_0= rule_SpeciesKey )
            {
            // InternalGaml.g:791:4: (lv_key_0_0= rule_SpeciesKey )
            // InternalGaml.g:792:5: lv_key_0_0= rule_SpeciesKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SpeciesAccess().getKey_SpeciesKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_7);
            lv_key_0_0=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SpeciesKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:809:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGaml.g:810:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGaml.g:810:4: (lv_name_1_0= RULE_ID )
            // InternalGaml.g:811:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getS_SpeciesAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_SpeciesRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            // InternalGaml.g:827:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==RULE_ID||LA13_0==15||(LA13_0>=90 && LA13_0<=102)) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // InternalGaml.g:828:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:828:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:829:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_SpeciesAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            // InternalGaml.g:846:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==32) ) {
                alt14=1;
            }
            else if ( (LA14_0==21) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // InternalGaml.g:847:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:847:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:848:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:848:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:849:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_SpeciesAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_SpeciesRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:867:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_SpeciesAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Species"


    // $ANTLR start "entryRuleS_Experiment"
    // InternalGaml.g:876:1: entryRuleS_Experiment returns [EObject current=null] : iv_ruleS_Experiment= ruleS_Experiment EOF ;
    public final EObject entryRuleS_Experiment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Experiment = null;


        try {
            // InternalGaml.g:876:53: (iv_ruleS_Experiment= ruleS_Experiment EOF )
            // InternalGaml.g:877:2: iv_ruleS_Experiment= ruleS_Experiment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ExperimentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Experiment=ruleS_Experiment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Experiment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Experiment"


    // $ANTLR start "ruleS_Experiment"
    // InternalGaml.g:883:1: ruleS_Experiment returns [EObject current=null] : ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_Experiment() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_2=null;
        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:889:2: ( ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:890:2: ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:890:2: ( ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:891:3: ( (lv_key_0_0= rule_ExperimentKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:891:3: ( (lv_key_0_0= rule_ExperimentKey ) )
            // InternalGaml.g:892:4: (lv_key_0_0= rule_ExperimentKey )
            {
            // InternalGaml.g:892:4: (lv_key_0_0= rule_ExperimentKey )
            // InternalGaml.g:893:5: lv_key_0_0= rule_ExperimentKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ExperimentAccess().getKey_ExperimentKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ExperimentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:910:3: ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) )
            // InternalGaml.g:911:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            {
            // InternalGaml.g:911:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            // InternalGaml.g:912:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            {
            // InternalGaml.g:912:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RULE_ID||LA15_0==36||(LA15_0>=38 && LA15_0<=81)) ) {
                alt15=1;
            }
            else if ( (LA15_0==RULE_STRING) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // InternalGaml.g:913:6: lv_name_1_1= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ExperimentAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_15);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:929:6: lv_name_1_2= RULE_STRING
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getS_ExperimentAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getS_ExperimentRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalGaml.g:946:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==RULE_ID||LA16_0==15||(LA16_0>=90 && LA16_0<=102)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // InternalGaml.g:947:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:947:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:948:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ExperimentAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // InternalGaml.g:965:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==32) ) {
                alt17=1;
            }
            else if ( (LA17_0==21) ) {
                alt17=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // InternalGaml.g:966:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:966:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:967:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:967:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:968:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ExperimentAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ExperimentRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:986:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_ExperimentAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Experiment"


    // $ANTLR start "entryRuleStatement"
    // InternalGaml.g:995:1: entryRuleStatement returns [EObject current=null] : iv_ruleStatement= ruleStatement EOF ;
    public final EObject entryRuleStatement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStatement = null;


        try {
            // InternalGaml.g:995:50: (iv_ruleStatement= ruleStatement EOF )
            // InternalGaml.g:996:2: iv_ruleStatement= ruleStatement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStatement=ruleStatement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStatement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStatement"


    // $ANTLR start "ruleStatement"
    // InternalGaml.g:1002:1: ruleStatement returns [EObject current=null] : ( ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) ) | this_S_Display_10= ruleS_Display ) ;
    public final EObject ruleStatement() throws RecognitionException {
        EObject current = null;

        EObject this_S_Declaration_0 = null;

        EObject this_S_Assignment_1 = null;

        EObject this_S_1Expr_Facets_BlockOrEnd_2 = null;

        EObject this_S_Other_3 = null;

        EObject this_S_Do_4 = null;

        EObject this_S_Return_5 = null;

        EObject this_S_Solve_6 = null;

        EObject this_S_If_7 = null;

        EObject this_S_Try_8 = null;

        EObject this_S_Equations_9 = null;

        EObject this_S_Display_10 = null;



        	enterRule();

        try {
            // InternalGaml.g:1008:2: ( ( ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) ) | this_S_Display_10= ruleS_Display ) )
            // InternalGaml.g:1009:2: ( ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) ) | this_S_Display_10= ruleS_Display )
            {
            // InternalGaml.g:1009:2: ( ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) ) | this_S_Display_10= ruleS_Display )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=RULE_ID && LA20_0<=RULE_KEYWORD)||(LA20_0>=23 && LA20_0<=24)||LA20_0==26||(LA20_0>=28 && LA20_0<=29)||(LA20_0>=31 && LA20_0<=32)||(LA20_0>=36 && LA20_0<=81)||(LA20_0>=90 && LA20_0<=101)||LA20_0==112||(LA20_0>=116 && LA20_0<=119)) ) {
                alt20=1;
            }
            else if ( (LA20_0==35) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // InternalGaml.g:1010:3: ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) )
                    {
                    // InternalGaml.g:1010:3: ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) )
                    int alt19=2;
                    alt19 = dfa19.predict(input);
                    switch (alt19) {
                        case 1 :
                            // InternalGaml.g:1011:4: ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration )
                            {
                            // InternalGaml.g:1011:4: ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration )
                            // InternalGaml.g:1012:5: ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getStatementAccess().getS_DeclarationParserRuleCall_0_0());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Declaration_0=ruleS_Declaration();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Declaration_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGaml.g:1023:4: ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations )
                            {
                            // InternalGaml.g:1023:4: ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations )
                            int alt18=9;
                            alt18 = dfa18.predict(input);
                            switch (alt18) {
                                case 1 :
                                    // InternalGaml.g:1024:5: ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment )
                                    {
                                    // InternalGaml.g:1024:5: ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment )
                                    // InternalGaml.g:1025:6: ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment
                                    {
                                    if ( state.backtracking==0 ) {

                                      						newCompositeNode(grammarAccess.getStatementAccess().getS_AssignmentParserRuleCall_0_1_0());
                                      					
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Assignment_1=ruleS_Assignment();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      						current = this_S_Assignment_1;
                                      						afterParserOrEnumRuleCall();
                                      					
                                    }

                                    }


                                    }
                                    break;
                                case 2 :
                                    // InternalGaml.g:1036:5: this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_1Expr_Facets_BlockOrEndParserRuleCall_0_1_1());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_1Expr_Facets_BlockOrEnd_2=ruleS_1Expr_Facets_BlockOrEnd();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_1Expr_Facets_BlockOrEnd_2;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalGaml.g:1045:5: this_S_Other_3= ruleS_Other
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_OtherParserRuleCall_0_1_2());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Other_3=ruleS_Other();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Other_3;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 4 :
                                    // InternalGaml.g:1054:5: this_S_Do_4= ruleS_Do
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_DoParserRuleCall_0_1_3());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Do_4=ruleS_Do();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Do_4;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 5 :
                                    // InternalGaml.g:1063:5: this_S_Return_5= ruleS_Return
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_ReturnParserRuleCall_0_1_4());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Return_5=ruleS_Return();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Return_5;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 6 :
                                    // InternalGaml.g:1072:5: this_S_Solve_6= ruleS_Solve
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_SolveParserRuleCall_0_1_5());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Solve_6=ruleS_Solve();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Solve_6;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 7 :
                                    // InternalGaml.g:1081:5: this_S_If_7= ruleS_If
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_IfParserRuleCall_0_1_6());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_If_7=ruleS_If();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_If_7;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 8 :
                                    // InternalGaml.g:1090:5: this_S_Try_8= ruleS_Try
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_TryParserRuleCall_0_1_7());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Try_8=ruleS_Try();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Try_8;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;
                                case 9 :
                                    // InternalGaml.g:1099:5: this_S_Equations_9= ruleS_Equations
                                    {
                                    if ( state.backtracking==0 ) {

                                      					newCompositeNode(grammarAccess.getStatementAccess().getS_EquationsParserRuleCall_0_1_8());
                                      				
                                    }
                                    pushFollow(FOLLOW_2);
                                    this_S_Equations_9=ruleS_Equations();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      					current = this_S_Equations_9;
                                      					afterParserOrEnumRuleCall();
                                      				
                                    }

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1110:3: this_S_Display_10= ruleS_Display
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getStatementAccess().getS_DisplayParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Display_10=ruleS_Display();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Display_10;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStatement"


    // $ANTLR start "entryRuleS_1Expr_Facets_BlockOrEnd"
    // InternalGaml.g:1122:1: entryRuleS_1Expr_Facets_BlockOrEnd returns [EObject current=null] : iv_ruleS_1Expr_Facets_BlockOrEnd= ruleS_1Expr_Facets_BlockOrEnd EOF ;
    public final EObject entryRuleS_1Expr_Facets_BlockOrEnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_1Expr_Facets_BlockOrEnd = null;


        try {
            // InternalGaml.g:1122:66: (iv_ruleS_1Expr_Facets_BlockOrEnd= ruleS_1Expr_Facets_BlockOrEnd EOF )
            // InternalGaml.g:1123:2: iv_ruleS_1Expr_Facets_BlockOrEnd= ruleS_1Expr_Facets_BlockOrEnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_1Expr_Facets_BlockOrEnd=ruleS_1Expr_Facets_BlockOrEnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_1Expr_Facets_BlockOrEnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_1Expr_Facets_BlockOrEnd"


    // $ANTLR start "ruleS_1Expr_Facets_BlockOrEnd"
    // InternalGaml.g:1129:1: ruleS_1Expr_Facets_BlockOrEnd returns [EObject current=null] : ( ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_1Expr_Facets_BlockOrEnd() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1135:2: ( ( ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:1136:2: ( ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:1136:2: ( ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:1137:3: ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:1137:3: ( (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key ) )
            // InternalGaml.g:1138:4: (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key )
            {
            // InternalGaml.g:1138:4: (lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key )
            // InternalGaml.g:1139:5: lv_key_0_0= rule_1Expr_Facets_BlockOrEnd_Key
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getKey_1Expr_Facets_BlockOrEnd_KeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_0_0=rule_1Expr_Facets_BlockOrEnd_Key();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._1Expr_Facets_BlockOrEnd_Key");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1156:3: ( (lv_expr_1_0= ruleExpression ) )
            // InternalGaml.g:1157:4: (lv_expr_1_0= ruleExpression )
            {
            // InternalGaml.g:1157:4: (lv_expr_1_0= ruleExpression )
            // InternalGaml.g:1158:5: lv_expr_1_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1175:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==RULE_ID||LA21_0==15||(LA21_0>=90 && LA21_0<=102)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // InternalGaml.g:1176:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:1176:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:1177:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            // InternalGaml.g:1194:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==32) ) {
                alt22=1;
            }
            else if ( (LA22_0==21) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalGaml.g:1195:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:1195:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:1196:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:1196:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:1197:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_1Expr_Facets_BlockOrEndRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1215:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_1Expr_Facets_BlockOrEndAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_1Expr_Facets_BlockOrEnd"


    // $ANTLR start "entryRuleS_Do"
    // InternalGaml.g:1224:1: entryRuleS_Do returns [EObject current=null] : iv_ruleS_Do= ruleS_Do EOF ;
    public final EObject entryRuleS_Do() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Do = null;


        try {
            // InternalGaml.g:1224:45: (iv_ruleS_Do= ruleS_Do EOF )
            // InternalGaml.g:1225:2: iv_ruleS_Do= ruleS_Do EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DoRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Do=ruleS_Do();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Do; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Do"


    // $ANTLR start "ruleS_Do"
    // InternalGaml.g:1231:1: ruleS_Do returns [EObject current=null] : ( ( (lv_key_0_0= rule_DoKey ) ) ( (lv_expr_1_0= ruleAbstractRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_Do() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1237:2: ( ( ( (lv_key_0_0= rule_DoKey ) ) ( (lv_expr_1_0= ruleAbstractRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:1238:2: ( ( (lv_key_0_0= rule_DoKey ) ) ( (lv_expr_1_0= ruleAbstractRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:1238:2: ( ( (lv_key_0_0= rule_DoKey ) ) ( (lv_expr_1_0= ruleAbstractRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:1239:3: ( (lv_key_0_0= rule_DoKey ) ) ( (lv_expr_1_0= ruleAbstractRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:1239:3: ( (lv_key_0_0= rule_DoKey ) )
            // InternalGaml.g:1240:4: (lv_key_0_0= rule_DoKey )
            {
            // InternalGaml.g:1240:4: (lv_key_0_0= rule_DoKey )
            // InternalGaml.g:1241:5: lv_key_0_0= rule_DoKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DoAccess().getKey_DoKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_DoKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DoRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._DoKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1258:3: ( (lv_expr_1_0= ruleAbstractRef ) )
            // InternalGaml.g:1259:4: (lv_expr_1_0= ruleAbstractRef )
            {
            // InternalGaml.g:1259:4: (lv_expr_1_0= ruleAbstractRef )
            // InternalGaml.g:1260:5: lv_expr_1_0= ruleAbstractRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DoAccess().getExprAbstractRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleAbstractRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DoRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.AbstractRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1277:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==RULE_ID||LA23_0==15||(LA23_0>=90 && LA23_0<=102)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // InternalGaml.g:1278:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:1278:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:1279:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DoAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DoRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);

            // InternalGaml.g:1296:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==32) ) {
                alt24=1;
            }
            else if ( (LA24_0==21) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalGaml.g:1297:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:1297:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:1298:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:1298:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:1299:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DoAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DoRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1317:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_DoAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Do"


    // $ANTLR start "entryRuleS_Loop"
    // InternalGaml.g:1326:1: entryRuleS_Loop returns [EObject current=null] : iv_ruleS_Loop= ruleS_Loop EOF ;
    public final EObject entryRuleS_Loop() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Loop = null;


        try {
            // InternalGaml.g:1326:47: (iv_ruleS_Loop= ruleS_Loop EOF )
            // InternalGaml.g:1327:2: iv_ruleS_Loop= ruleS_Loop EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_LoopRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Loop=ruleS_Loop();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Loop; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Loop"


    // $ANTLR start "ruleS_Loop"
    // InternalGaml.g:1333:1: ruleS_Loop returns [EObject current=null] : ( ( (lv_key_0_0= 'loop' ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleBlock ) ) ) ;
    public final EObject ruleS_Loop() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1339:2: ( ( ( (lv_key_0_0= 'loop' ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleBlock ) ) ) )
            // InternalGaml.g:1340:2: ( ( (lv_key_0_0= 'loop' ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleBlock ) ) )
            {
            // InternalGaml.g:1340:2: ( ( (lv_key_0_0= 'loop' ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleBlock ) ) )
            // InternalGaml.g:1341:3: ( (lv_key_0_0= 'loop' ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleBlock ) )
            {
            // InternalGaml.g:1341:3: ( (lv_key_0_0= 'loop' ) )
            // InternalGaml.g:1342:4: (lv_key_0_0= 'loop' )
            {
            // InternalGaml.g:1342:4: (lv_key_0_0= 'loop' )
            // InternalGaml.g:1343:5: lv_key_0_0= 'loop'
            {
            lv_key_0_0=(Token)match(input,23,FOLLOW_16); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_LoopAccess().getKeyLoopKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_LoopRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "loop");
              				
            }

            }


            }

            // InternalGaml.g:1355:3: ( (lv_name_1_0= ruleValid_ID ) )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==36||(LA25_0>=38 && LA25_0<=81)) ) {
                alt25=1;
            }
            else if ( (LA25_0==RULE_ID) ) {
                int LA25_2 = input.LA(2);

                if ( (LA25_2==RULE_ID||LA25_2==15||LA25_2==32||(LA25_2>=90 && LA25_2<=102)) ) {
                    alt25=1;
                }
            }
            switch (alt25) {
                case 1 :
                    // InternalGaml.g:1356:4: (lv_name_1_0= ruleValid_ID )
                    {
                    // InternalGaml.g:1356:4: (lv_name_1_0= ruleValid_ID )
                    // InternalGaml.g:1357:5: lv_name_1_0= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_LoopAccess().getNameValid_IDParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_16);
                    lv_name_1_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_LoopRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_1_0,
                      						"gama.core.lang.Gaml.Valid_ID");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalGaml.g:1374:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==RULE_ID||LA26_0==15||(LA26_0>=90 && LA26_0<=102)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // InternalGaml.g:1375:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:1375:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:1376:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_LoopAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_16);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_LoopRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            // InternalGaml.g:1393:3: ( (lv_block_3_0= ruleBlock ) )
            // InternalGaml.g:1394:4: (lv_block_3_0= ruleBlock )
            {
            // InternalGaml.g:1394:4: (lv_block_3_0= ruleBlock )
            // InternalGaml.g:1395:5: lv_block_3_0= ruleBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_LoopAccess().getBlockBlockParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_3_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_LoopRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_3_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Loop"


    // $ANTLR start "entryRuleS_If"
    // InternalGaml.g:1416:1: entryRuleS_If returns [EObject current=null] : iv_ruleS_If= ruleS_If EOF ;
    public final EObject entryRuleS_If() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_If = null;


        try {
            // InternalGaml.g:1416:45: (iv_ruleS_If= ruleS_If EOF )
            // InternalGaml.g:1417:2: iv_ruleS_If= ruleS_If EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_IfRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_If=ruleS_If();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_If; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_If"


    // $ANTLR start "ruleS_If"
    // InternalGaml.g:1423:1: ruleS_If returns [EObject current=null] : ( ( (lv_key_0_0= 'if' ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_block_2_0= ruleBlock ) ) ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )? ) ;
    public final EObject ruleS_If() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_expr_1_0 = null;

        EObject lv_block_2_0 = null;

        EObject lv_else_4_1 = null;

        EObject lv_else_4_2 = null;



        	enterRule();

        try {
            // InternalGaml.g:1429:2: ( ( ( (lv_key_0_0= 'if' ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_block_2_0= ruleBlock ) ) ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )? ) )
            // InternalGaml.g:1430:2: ( ( (lv_key_0_0= 'if' ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_block_2_0= ruleBlock ) ) ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )? )
            {
            // InternalGaml.g:1430:2: ( ( (lv_key_0_0= 'if' ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_block_2_0= ruleBlock ) ) ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )? )
            // InternalGaml.g:1431:3: ( (lv_key_0_0= 'if' ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_block_2_0= ruleBlock ) ) ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )?
            {
            // InternalGaml.g:1431:3: ( (lv_key_0_0= 'if' ) )
            // InternalGaml.g:1432:4: (lv_key_0_0= 'if' )
            {
            // InternalGaml.g:1432:4: (lv_key_0_0= 'if' )
            // InternalGaml.g:1433:5: lv_key_0_0= 'if'
            {
            lv_key_0_0=(Token)match(input,24,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_IfAccess().getKeyIfKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_IfRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "if");
              				
            }

            }


            }

            // InternalGaml.g:1445:3: ( (lv_expr_1_0= ruleExpression ) )
            // InternalGaml.g:1446:4: (lv_expr_1_0= ruleExpression )
            {
            // InternalGaml.g:1446:4: (lv_expr_1_0= ruleExpression )
            // InternalGaml.g:1447:5: lv_expr_1_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_IfAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_3);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_IfRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1464:3: ( (lv_block_2_0= ruleBlock ) )
            // InternalGaml.g:1465:4: (lv_block_2_0= ruleBlock )
            {
            // InternalGaml.g:1465:4: (lv_block_2_0= ruleBlock )
            // InternalGaml.g:1466:5: lv_block_2_0= ruleBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_IfAccess().getBlockBlockParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_17);
            lv_block_2_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_IfRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_2_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1483:3: ( ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) ) )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==25) && (synpred4_InternalGaml())) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // InternalGaml.g:1484:4: ( ( 'else' )=>otherlv_3= 'else' ) ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) )
                    {
                    // InternalGaml.g:1484:4: ( ( 'else' )=>otherlv_3= 'else' )
                    // InternalGaml.g:1485:5: ( 'else' )=>otherlv_3= 'else'
                    {
                    otherlv_3=(Token)match(input,25,FOLLOW_18); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getS_IfAccess().getElseKeyword_3_0());
                      				
                    }

                    }

                    // InternalGaml.g:1491:4: ( ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) ) )
                    // InternalGaml.g:1492:5: ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) )
                    {
                    // InternalGaml.g:1492:5: ( (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock ) )
                    // InternalGaml.g:1493:6: (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock )
                    {
                    // InternalGaml.g:1493:6: (lv_else_4_1= ruleS_If | lv_else_4_2= ruleBlock )
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==24) ) {
                        alt27=1;
                    }
                    else if ( (LA27_0==32) ) {
                        alt27=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }
                    switch (alt27) {
                        case 1 :
                            // InternalGaml.g:1494:7: lv_else_4_1= ruleS_If
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getS_IfAccess().getElseS_IfParserRuleCall_3_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_else_4_1=ruleS_If();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getS_IfRule());
                              							}
                              							set(
                              								current,
                              								"else",
                              								lv_else_4_1,
                              								"gama.core.lang.Gaml.S_If");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalGaml.g:1510:7: lv_else_4_2= ruleBlock
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getS_IfAccess().getElseBlockParserRuleCall_3_1_0_1());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_else_4_2=ruleBlock();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getS_IfRule());
                              							}
                              							set(
                              								current,
                              								"else",
                              								lv_else_4_2,
                              								"gama.core.lang.Gaml.Block");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_If"


    // $ANTLR start "entryRuleS_Try"
    // InternalGaml.g:1533:1: entryRuleS_Try returns [EObject current=null] : iv_ruleS_Try= ruleS_Try EOF ;
    public final EObject entryRuleS_Try() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Try = null;


        try {
            // InternalGaml.g:1533:46: (iv_ruleS_Try= ruleS_Try EOF )
            // InternalGaml.g:1534:2: iv_ruleS_Try= ruleS_Try EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_TryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Try=ruleS_Try();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Try; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Try"


    // $ANTLR start "ruleS_Try"
    // InternalGaml.g:1540:1: ruleS_Try returns [EObject current=null] : ( ( (lv_key_0_0= 'try' ) ) ( (lv_block_1_0= ruleBlock ) ) ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )? ) ;
    public final EObject ruleS_Try() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token otherlv_2=null;
        EObject lv_block_1_0 = null;

        EObject lv_catch_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1546:2: ( ( ( (lv_key_0_0= 'try' ) ) ( (lv_block_1_0= ruleBlock ) ) ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )? ) )
            // InternalGaml.g:1547:2: ( ( (lv_key_0_0= 'try' ) ) ( (lv_block_1_0= ruleBlock ) ) ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )? )
            {
            // InternalGaml.g:1547:2: ( ( (lv_key_0_0= 'try' ) ) ( (lv_block_1_0= ruleBlock ) ) ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )? )
            // InternalGaml.g:1548:3: ( (lv_key_0_0= 'try' ) ) ( (lv_block_1_0= ruleBlock ) ) ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )?
            {
            // InternalGaml.g:1548:3: ( (lv_key_0_0= 'try' ) )
            // InternalGaml.g:1549:4: (lv_key_0_0= 'try' )
            {
            // InternalGaml.g:1549:4: (lv_key_0_0= 'try' )
            // InternalGaml.g:1550:5: lv_key_0_0= 'try'
            {
            lv_key_0_0=(Token)match(input,26,FOLLOW_3); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_TryAccess().getKeyTryKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_TryRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "try");
              				
            }

            }


            }

            // InternalGaml.g:1562:3: ( (lv_block_1_0= ruleBlock ) )
            // InternalGaml.g:1563:4: (lv_block_1_0= ruleBlock )
            {
            // InternalGaml.g:1563:4: (lv_block_1_0= ruleBlock )
            // InternalGaml.g:1564:5: lv_block_1_0= ruleBlock
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_TryAccess().getBlockBlockParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_19);
            lv_block_1_0=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_TryRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_1_0,
              						"gama.core.lang.Gaml.Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1581:3: ( ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) ) )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==27) && (synpred5_InternalGaml())) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalGaml.g:1582:4: ( ( 'catch' )=>otherlv_2= 'catch' ) ( (lv_catch_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:1582:4: ( ( 'catch' )=>otherlv_2= 'catch' )
                    // InternalGaml.g:1583:5: ( 'catch' )=>otherlv_2= 'catch'
                    {
                    otherlv_2=(Token)match(input,27,FOLLOW_3); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_2, grammarAccess.getS_TryAccess().getCatchKeyword_2_0());
                      				
                    }

                    }

                    // InternalGaml.g:1589:4: ( (lv_catch_3_0= ruleBlock ) )
                    // InternalGaml.g:1590:5: (lv_catch_3_0= ruleBlock )
                    {
                    // InternalGaml.g:1590:5: (lv_catch_3_0= ruleBlock )
                    // InternalGaml.g:1591:6: lv_catch_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_TryAccess().getCatchBlockParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_catch_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_TryRule());
                      						}
                      						set(
                      							current,
                      							"catch",
                      							lv_catch_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Try"


    // $ANTLR start "entryRuleS_Other"
    // InternalGaml.g:1613:1: entryRuleS_Other returns [EObject current=null] : iv_ruleS_Other= ruleS_Other EOF ;
    public final EObject entryRuleS_Other() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Other = null;


        try {
            // InternalGaml.g:1613:48: (iv_ruleS_Other= ruleS_Other EOF )
            // InternalGaml.g:1614:2: iv_ruleS_Other= ruleS_Other EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_OtherRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Other=ruleS_Other();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Other; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Other"


    // $ANTLR start "ruleS_Other"
    // InternalGaml.g:1620:1: ruleS_Other returns [EObject current=null] : ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) ) ;
    public final EObject ruleS_Other() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token otherlv_3=null;
        EObject lv_facets_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1626:2: ( ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) ) )
            // InternalGaml.g:1627:2: ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) )
            {
            // InternalGaml.g:1627:2: ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' ) )
            // InternalGaml.g:1628:3: ( (lv_key_0_0= RULE_ID ) ) ( (lv_facets_1_0= ruleFacet ) )* ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' )
            {
            // InternalGaml.g:1628:3: ( (lv_key_0_0= RULE_ID ) )
            // InternalGaml.g:1629:4: (lv_key_0_0= RULE_ID )
            {
            // InternalGaml.g:1629:4: (lv_key_0_0= RULE_ID )
            // InternalGaml.g:1630:5: lv_key_0_0= RULE_ID
            {
            lv_key_0_0=(Token)match(input,RULE_ID,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_OtherAccess().getKeyIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_OtherRule());
              					}
              					setWithLastConsumed(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            // InternalGaml.g:1646:3: ( (lv_facets_1_0= ruleFacet ) )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==RULE_ID||LA30_0==15||(LA30_0>=90 && LA30_0<=102)) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // InternalGaml.g:1647:4: (lv_facets_1_0= ruleFacet )
            	    {
            	    // InternalGaml.g:1647:4: (lv_facets_1_0= ruleFacet )
            	    // InternalGaml.g:1648:5: lv_facets_1_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_OtherAccess().getFacetsFacetParserRuleCall_1_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_1_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_OtherRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_1_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            // InternalGaml.g:1665:3: ( ( (lv_block_2_0= ruleBlock ) ) | otherlv_3= ';' )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==32) ) {
                alt31=1;
            }
            else if ( (LA31_0==21) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // InternalGaml.g:1666:4: ( (lv_block_2_0= ruleBlock ) )
                    {
                    // InternalGaml.g:1666:4: ( (lv_block_2_0= ruleBlock ) )
                    // InternalGaml.g:1667:5: (lv_block_2_0= ruleBlock )
                    {
                    // InternalGaml.g:1667:5: (lv_block_2_0= ruleBlock )
                    // InternalGaml.g:1668:6: lv_block_2_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_OtherAccess().getBlockBlockParserRuleCall_2_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_OtherRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1686:4: otherlv_3= ';'
                    {
                    otherlv_3=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_OtherAccess().getSemicolonKeyword_2_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Other"


    // $ANTLR start "entryRuleS_Return"
    // InternalGaml.g:1695:1: entryRuleS_Return returns [EObject current=null] : iv_ruleS_Return= ruleS_Return EOF ;
    public final EObject entryRuleS_Return() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Return = null;


        try {
            // InternalGaml.g:1695:49: (iv_ruleS_Return= ruleS_Return EOF )
            // InternalGaml.g:1696:2: iv_ruleS_Return= ruleS_Return EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ReturnRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Return=ruleS_Return();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Return; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Return"


    // $ANTLR start "ruleS_Return"
    // InternalGaml.g:1702:1: ruleS_Return returns [EObject current=null] : ( ( (lv_key_0_0= 'return' ) ) ( (lv_expr_1_0= ruleExpression ) )? otherlv_2= ';' ) ;
    public final EObject ruleS_Return() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token otherlv_2=null;
        EObject lv_expr_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1708:2: ( ( ( (lv_key_0_0= 'return' ) ) ( (lv_expr_1_0= ruleExpression ) )? otherlv_2= ';' ) )
            // InternalGaml.g:1709:2: ( ( (lv_key_0_0= 'return' ) ) ( (lv_expr_1_0= ruleExpression ) )? otherlv_2= ';' )
            {
            // InternalGaml.g:1709:2: ( ( (lv_key_0_0= 'return' ) ) ( (lv_expr_1_0= ruleExpression ) )? otherlv_2= ';' )
            // InternalGaml.g:1710:3: ( (lv_key_0_0= 'return' ) ) ( (lv_expr_1_0= ruleExpression ) )? otherlv_2= ';'
            {
            // InternalGaml.g:1710:3: ( (lv_key_0_0= 'return' ) )
            // InternalGaml.g:1711:4: (lv_key_0_0= 'return' )
            {
            // InternalGaml.g:1711:4: (lv_key_0_0= 'return' )
            // InternalGaml.g:1712:5: lv_key_0_0= 'return'
            {
            lv_key_0_0=(Token)match(input,28,FOLLOW_20); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_ReturnAccess().getKeyReturnKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_ReturnRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "return");
              				
            }

            }


            }

            // InternalGaml.g:1724:3: ( (lv_expr_1_0= ruleExpression ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>=RULE_ID && LA32_0<=RULE_KEYWORD)||LA32_0==29||LA32_0==32||LA32_0==36||(LA32_0>=38 && LA32_0<=81)||(LA32_0>=90 && LA32_0<=101)||LA32_0==112||(LA32_0>=116 && LA32_0<=119)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalGaml.g:1725:4: (lv_expr_1_0= ruleExpression )
                    {
                    // InternalGaml.g:1725:4: (lv_expr_1_0= ruleExpression )
                    // InternalGaml.g:1726:5: lv_expr_1_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_ReturnAccess().getExprExpressionParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_21);
                    lv_expr_1_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_ReturnRule());
                      					}
                      					set(
                      						current,
                      						"expr",
                      						lv_expr_1_0,
                      						"gama.core.lang.Gaml.Expression");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_2=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_2, grammarAccess.getS_ReturnAccess().getSemicolonKeyword_2());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Return"


    // $ANTLR start "entryRuleS_Declaration"
    // InternalGaml.g:1751:1: entryRuleS_Declaration returns [EObject current=null] : iv_ruleS_Declaration= ruleS_Declaration EOF ;
    public final EObject entryRuleS_Declaration() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Declaration = null;


        try {
            // InternalGaml.g:1751:54: (iv_ruleS_Declaration= ruleS_Declaration EOF )
            // InternalGaml.g:1752:2: iv_ruleS_Declaration= ruleS_Declaration EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DeclarationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Declaration=ruleS_Declaration();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Declaration; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Declaration"


    // $ANTLR start "ruleS_Declaration"
    // InternalGaml.g:1758:1: ruleS_Declaration returns [EObject current=null] : ( ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition ) | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop | this_S_StringDefinition_5= ruleS_StringDefinition ) ;
    public final EObject ruleS_Declaration() throws RecognitionException {
        EObject current = null;

        EObject this_S_Definition_0 = null;

        EObject this_S_Species_1 = null;

        EObject this_S_Reflex_2 = null;

        EObject this_S_Action_3 = null;

        EObject this_S_Loop_4 = null;

        EObject this_S_StringDefinition_5 = null;



        	enterRule();

        try {
            // InternalGaml.g:1764:2: ( ( ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition ) | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop | this_S_StringDefinition_5= ruleS_StringDefinition ) )
            // InternalGaml.g:1765:2: ( ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition ) | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop | this_S_StringDefinition_5= ruleS_StringDefinition )
            {
            // InternalGaml.g:1765:2: ( ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition ) | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop | this_S_StringDefinition_5= ruleS_StringDefinition )
            int alt33=6;
            alt33 = dfa33.predict(input);
            switch (alt33) {
                case 1 :
                    // InternalGaml.g:1766:3: ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition )
                    {
                    // InternalGaml.g:1766:3: ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition )
                    // InternalGaml.g:1767:4: ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_DefinitionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Definition_0=ruleS_Definition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_S_Definition_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1778:3: this_S_Species_1= ruleS_Species
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_SpeciesParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Species_1=ruleS_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Species_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:1787:3: this_S_Reflex_2= ruleS_Reflex
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_ReflexParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Reflex_2=ruleS_Reflex();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Reflex_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:1796:3: this_S_Action_3= ruleS_Action
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_ActionParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Action_3=ruleS_Action();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Action_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:1805:3: this_S_Loop_4= ruleS_Loop
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_LoopParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Loop_4=ruleS_Loop();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Loop_4;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGaml.g:1814:3: this_S_StringDefinition_5= ruleS_StringDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getS_DeclarationAccess().getS_StringDefinitionParserRuleCall_5());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_StringDefinition_5=ruleS_StringDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_StringDefinition_5;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Declaration"


    // $ANTLR start "entryRuleS_Reflex"
    // InternalGaml.g:1826:1: entryRuleS_Reflex returns [EObject current=null] : iv_ruleS_Reflex= ruleS_Reflex EOF ;
    public final EObject entryRuleS_Reflex() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Reflex = null;


        try {
            // InternalGaml.g:1826:49: (iv_ruleS_Reflex= ruleS_Reflex EOF )
            // InternalGaml.g:1827:2: iv_ruleS_Reflex= ruleS_Reflex EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ReflexRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Reflex=ruleS_Reflex();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Reflex; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Reflex"


    // $ANTLR start "ruleS_Reflex"
    // InternalGaml.g:1833:1: ruleS_Reflex returns [EObject current=null] : ( ( (lv_key_0_0= rule_ReflexKey ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_Reflex() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1839:2: ( ( ( (lv_key_0_0= rule_ReflexKey ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:1840:2: ( ( (lv_key_0_0= rule_ReflexKey ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:1840:2: ( ( (lv_key_0_0= rule_ReflexKey ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:1841:3: ( (lv_key_0_0= rule_ReflexKey ) ) ( (lv_name_1_0= ruleValid_ID ) )? ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:1841:3: ( (lv_key_0_0= rule_ReflexKey ) )
            // InternalGaml.g:1842:4: (lv_key_0_0= rule_ReflexKey )
            {
            // InternalGaml.g:1842:4: (lv_key_0_0= rule_ReflexKey )
            // InternalGaml.g:1843:5: lv_key_0_0= rule_ReflexKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ReflexAccess().getKey_ReflexKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_key_0_0=rule_ReflexKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._ReflexKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1860:3: ( (lv_name_1_0= ruleValid_ID ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==36||(LA34_0>=38 && LA34_0<=81)) ) {
                alt34=1;
            }
            else if ( (LA34_0==RULE_ID) ) {
                int LA34_2 = input.LA(2);

                if ( (LA34_2==RULE_ID||LA34_2==15||LA34_2==21||LA34_2==32||(LA34_2>=90 && LA34_2<=102)) ) {
                    alt34=1;
                }
            }
            switch (alt34) {
                case 1 :
                    // InternalGaml.g:1861:4: (lv_name_1_0= ruleValid_ID )
                    {
                    // InternalGaml.g:1861:4: (lv_name_1_0= ruleValid_ID )
                    // InternalGaml.g:1862:5: lv_name_1_0= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getS_ReflexAccess().getNameValid_IDParserRuleCall_1_0());
                      				
                    }
                    pushFollow(FOLLOW_15);
                    lv_name_1_0=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
                      					}
                      					set(
                      						current,
                      						"name",
                      						lv_name_1_0,
                      						"gama.core.lang.Gaml.Valid_ID");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalGaml.g:1879:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==RULE_ID||LA35_0==15||(LA35_0>=90 && LA35_0<=102)) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalGaml.g:1880:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:1880:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:1881:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ReflexAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ReflexRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            // InternalGaml.g:1898:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==32) ) {
                alt36=1;
            }
            else if ( (LA36_0==21) ) {
                alt36=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // InternalGaml.g:1899:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:1899:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:1900:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:1900:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:1901:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ReflexAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ReflexRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:1919:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_ReflexAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Reflex"


    // $ANTLR start "entryRuleS_Definition"
    // InternalGaml.g:1928:1: entryRuleS_Definition returns [EObject current=null] : iv_ruleS_Definition= ruleS_Definition EOF ;
    public final EObject entryRuleS_Definition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Definition = null;


        try {
            // InternalGaml.g:1928:53: (iv_ruleS_Definition= ruleS_Definition EOF )
            // InternalGaml.g:1929:2: iv_ruleS_Definition= ruleS_Definition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Definition=ruleS_Definition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Definition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Definition"


    // $ANTLR start "ruleS_Definition"
    // InternalGaml.g:1935:1: ruleS_Definition returns [EObject current=null] : ( ( (lv_tkey_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )? ( (lv_facets_5_0= ruleFacet ) )* ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' ) ) ;
    public final EObject ruleS_Definition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_7=null;
        EObject lv_tkey_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_args_3_0 = null;

        EObject lv_facets_5_0 = null;

        EObject lv_block_6_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:1941:2: ( ( ( (lv_tkey_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )? ( (lv_facets_5_0= ruleFacet ) )* ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' ) ) )
            // InternalGaml.g:1942:2: ( ( (lv_tkey_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )? ( (lv_facets_5_0= ruleFacet ) )* ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' ) )
            {
            // InternalGaml.g:1942:2: ( ( (lv_tkey_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )? ( (lv_facets_5_0= ruleFacet ) )* ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' ) )
            // InternalGaml.g:1943:3: ( (lv_tkey_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )? ( (lv_facets_5_0= ruleFacet ) )* ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' )
            {
            // InternalGaml.g:1943:3: ( (lv_tkey_0_0= ruleTypeRef ) )
            // InternalGaml.g:1944:4: (lv_tkey_0_0= ruleTypeRef )
            {
            // InternalGaml.g:1944:4: (lv_tkey_0_0= ruleTypeRef )
            // InternalGaml.g:1945:5: lv_tkey_0_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DefinitionAccess().getTkeyTypeRefParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_tkey_0_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
              					}
              					set(
              						current,
              						"tkey",
              						lv_tkey_0_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1962:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:1963:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:1963:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:1964:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_22);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:1981:3: (otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==29) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalGaml.g:1982:4: otherlv_2= '(' ( (lv_args_3_0= ruleActionArguments ) ) otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,29,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getS_DefinitionAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    // InternalGaml.g:1986:4: ( (lv_args_3_0= ruleActionArguments ) )
                    // InternalGaml.g:1987:5: (lv_args_3_0= ruleActionArguments )
                    {
                    // InternalGaml.g:1987:5: (lv_args_3_0= ruleActionArguments )
                    // InternalGaml.g:1988:6: lv_args_3_0= ruleActionArguments
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DefinitionAccess().getArgsActionArgumentsParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_24);
                    lv_args_3_0=ruleActionArguments();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
                      						}
                      						set(
                      							current,
                      							"args",
                      							lv_args_3_0,
                      							"gama.core.lang.Gaml.ActionArguments");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_4=(Token)match(input,30,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_DefinitionAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGaml.g:2010:3: ( (lv_facets_5_0= ruleFacet ) )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==RULE_ID||LA38_0==15||(LA38_0>=90 && LA38_0<=102)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalGaml.g:2011:4: (lv_facets_5_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2011:4: (lv_facets_5_0= ruleFacet )
            	    // InternalGaml.g:2012:5: lv_facets_5_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DefinitionAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_5_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_5_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            // InternalGaml.g:2029:3: ( ( (lv_block_6_0= ruleBlock ) ) | otherlv_7= ';' )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==32) ) {
                alt39=1;
            }
            else if ( (LA39_0==21) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // InternalGaml.g:2030:4: ( (lv_block_6_0= ruleBlock ) )
                    {
                    // InternalGaml.g:2030:4: ( (lv_block_6_0= ruleBlock ) )
                    // InternalGaml.g:2031:5: (lv_block_6_0= ruleBlock )
                    {
                    // InternalGaml.g:2031:5: (lv_block_6_0= ruleBlock )
                    // InternalGaml.g:2032:6: lv_block_6_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DefinitionAccess().getBlockBlockParserRuleCall_4_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_6_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DefinitionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_6_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2050:4: otherlv_7= ';'
                    {
                    otherlv_7=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getS_DefinitionAccess().getSemicolonKeyword_4_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Definition"


    // $ANTLR start "entryRuleS_StringDefinition"
    // InternalGaml.g:2059:1: entryRuleS_StringDefinition returns [EObject current=null] : iv_ruleS_StringDefinition= ruleS_StringDefinition EOF ;
    public final EObject entryRuleS_StringDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_StringDefinition = null;


        try {
            // InternalGaml.g:2059:59: (iv_ruleS_StringDefinition= ruleS_StringDefinition EOF )
            // InternalGaml.g:2060:2: iv_ruleS_StringDefinition= ruleS_StringDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_StringDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_StringDefinition=ruleS_StringDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_StringDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_StringDefinition"


    // $ANTLR start "ruleS_StringDefinition"
    // InternalGaml.g:2066:1: ruleS_StringDefinition returns [EObject current=null] : ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_name_1_0= RULE_STRING ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_StringDefinition() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token lv_name_1_0=null;
        Token otherlv_4=null;
        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2072:2: ( ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_name_1_0= RULE_STRING ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:2073:2: ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_name_1_0= RULE_STRING ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:2073:2: ( ( (lv_key_0_0= RULE_ID ) ) ( (lv_name_1_0= RULE_STRING ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:2074:3: ( (lv_key_0_0= RULE_ID ) ) ( (lv_name_1_0= RULE_STRING ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:2074:3: ( (lv_key_0_0= RULE_ID ) )
            // InternalGaml.g:2075:4: (lv_key_0_0= RULE_ID )
            {
            // InternalGaml.g:2075:4: (lv_key_0_0= RULE_ID )
            // InternalGaml.g:2076:5: lv_key_0_0= RULE_ID
            {
            lv_key_0_0=(Token)match(input,RULE_ID,FOLLOW_10); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_StringDefinitionAccess().getKeyIDTerminalRuleCall_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_StringDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }

            // InternalGaml.g:2092:3: ( (lv_name_1_0= RULE_STRING ) )
            // InternalGaml.g:2093:4: (lv_name_1_0= RULE_STRING )
            {
            // InternalGaml.g:2093:4: (lv_name_1_0= RULE_STRING )
            // InternalGaml.g:2094:5: lv_name_1_0= RULE_STRING
            {
            lv_name_1_0=(Token)match(input,RULE_STRING,FOLLOW_15); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getS_StringDefinitionAccess().getNameSTRINGTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_StringDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.STRING");
              				
            }

            }


            }

            // InternalGaml.g:2110:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==RULE_ID||LA40_0==15||(LA40_0>=90 && LA40_0<=102)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalGaml.g:2111:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2111:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:2112:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_StringDefinitionAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_StringDefinitionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

            // InternalGaml.g:2129:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==32) ) {
                alt41=1;
            }
            else if ( (LA41_0==21) ) {
                alt41=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // InternalGaml.g:2130:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:2130:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:2131:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:2131:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:2132:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_StringDefinitionAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_StringDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2150:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_StringDefinitionAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_StringDefinition"


    // $ANTLR start "entryRuleS_Action"
    // InternalGaml.g:2159:1: entryRuleS_Action returns [EObject current=null] : iv_ruleS_Action= ruleS_Action EOF ;
    public final EObject entryRuleS_Action() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Action = null;


        try {
            // InternalGaml.g:2159:49: (iv_ruleS_Action= ruleS_Action EOF )
            // InternalGaml.g:2160:2: iv_ruleS_Action= ruleS_Action EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_ActionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Action=ruleS_Action();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Action; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Action"


    // $ANTLR start "ruleS_Action"
    // InternalGaml.g:2166:1: ruleS_Action returns [EObject current=null] : ( () ( (lv_key_1_0= 'action' ) ) ( (lv_name_2_0= ruleValid_ID ) ) (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )? ( (lv_facets_6_0= ruleFacet ) )* ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' ) ) ;
    public final EObject ruleS_Action() throws RecognitionException {
        EObject current = null;

        Token lv_key_1_0=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        AntlrDatatypeRuleToken lv_name_2_0 = null;

        EObject lv_args_4_0 = null;

        EObject lv_facets_6_0 = null;

        EObject lv_block_7_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2172:2: ( ( () ( (lv_key_1_0= 'action' ) ) ( (lv_name_2_0= ruleValid_ID ) ) (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )? ( (lv_facets_6_0= ruleFacet ) )* ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' ) ) )
            // InternalGaml.g:2173:2: ( () ( (lv_key_1_0= 'action' ) ) ( (lv_name_2_0= ruleValid_ID ) ) (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )? ( (lv_facets_6_0= ruleFacet ) )* ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' ) )
            {
            // InternalGaml.g:2173:2: ( () ( (lv_key_1_0= 'action' ) ) ( (lv_name_2_0= ruleValid_ID ) ) (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )? ( (lv_facets_6_0= ruleFacet ) )* ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' ) )
            // InternalGaml.g:2174:3: () ( (lv_key_1_0= 'action' ) ) ( (lv_name_2_0= ruleValid_ID ) ) (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )? ( (lv_facets_6_0= ruleFacet ) )* ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' )
            {
            // InternalGaml.g:2174:3: ()
            // InternalGaml.g:2175:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getS_ActionAccess().getS_ActionAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:2181:3: ( (lv_key_1_0= 'action' ) )
            // InternalGaml.g:2182:4: (lv_key_1_0= 'action' )
            {
            // InternalGaml.g:2182:4: (lv_key_1_0= 'action' )
            // InternalGaml.g:2183:5: lv_key_1_0= 'action'
            {
            lv_key_1_0=(Token)match(input,31,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_1_0, grammarAccess.getS_ActionAccess().getKeyActionKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_ActionRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_1_0, "action");
              				
            }

            }


            }

            // InternalGaml.g:2195:3: ( (lv_name_2_0= ruleValid_ID ) )
            // InternalGaml.g:2196:4: (lv_name_2_0= ruleValid_ID )
            {
            // InternalGaml.g:2196:4: (lv_name_2_0= ruleValid_ID )
            // InternalGaml.g:2197:5: lv_name_2_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_ActionAccess().getNameValid_IDParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_22);
            lv_name_2_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_ActionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_2_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2214:3: (otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')' )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==29) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalGaml.g:2215:4: otherlv_3= '(' ( (lv_args_4_0= ruleActionArguments ) ) otherlv_5= ')'
                    {
                    otherlv_3=(Token)match(input,29,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_3, grammarAccess.getS_ActionAccess().getLeftParenthesisKeyword_3_0());
                      			
                    }
                    // InternalGaml.g:2219:4: ( (lv_args_4_0= ruleActionArguments ) )
                    // InternalGaml.g:2220:5: (lv_args_4_0= ruleActionArguments )
                    {
                    // InternalGaml.g:2220:5: (lv_args_4_0= ruleActionArguments )
                    // InternalGaml.g:2221:6: lv_args_4_0= ruleActionArguments
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ActionAccess().getArgsActionArgumentsParserRuleCall_3_1_0());
                      					
                    }
                    pushFollow(FOLLOW_24);
                    lv_args_4_0=ruleActionArguments();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ActionRule());
                      						}
                      						set(
                      							current,
                      							"args",
                      							lv_args_4_0,
                      							"gama.core.lang.Gaml.ActionArguments");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    otherlv_5=(Token)match(input,30,FOLLOW_15); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getS_ActionAccess().getRightParenthesisKeyword_3_2());
                      			
                    }

                    }
                    break;

            }

            // InternalGaml.g:2243:3: ( (lv_facets_6_0= ruleFacet ) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==RULE_ID||LA43_0==15||(LA43_0>=90 && LA43_0<=102)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // InternalGaml.g:2244:4: (lv_facets_6_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2244:4: (lv_facets_6_0= ruleFacet )
            	    // InternalGaml.g:2245:5: lv_facets_6_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_ActionAccess().getFacetsFacetParserRuleCall_4_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_6_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_ActionRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_6_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            // InternalGaml.g:2262:3: ( ( (lv_block_7_0= ruleBlock ) ) | otherlv_8= ';' )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==32) ) {
                alt44=1;
            }
            else if ( (LA44_0==21) ) {
                alt44=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // InternalGaml.g:2263:4: ( (lv_block_7_0= ruleBlock ) )
                    {
                    // InternalGaml.g:2263:4: ( (lv_block_7_0= ruleBlock ) )
                    // InternalGaml.g:2264:5: (lv_block_7_0= ruleBlock )
                    {
                    // InternalGaml.g:2264:5: (lv_block_7_0= ruleBlock )
                    // InternalGaml.g:2265:6: lv_block_7_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_ActionAccess().getBlockBlockParserRuleCall_5_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_7_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_ActionRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_7_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2283:4: otherlv_8= ';'
                    {
                    otherlv_8=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getS_ActionAccess().getSemicolonKeyword_5_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Action"


    // $ANTLR start "entryRuleS_Assignment"
    // InternalGaml.g:2292:1: entryRuleS_Assignment returns [EObject current=null] : iv_ruleS_Assignment= ruleS_Assignment EOF ;
    public final EObject entryRuleS_Assignment() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Assignment = null;


        try {
            // InternalGaml.g:2292:53: (iv_ruleS_Assignment= ruleS_Assignment EOF )
            // InternalGaml.g:2293:2: iv_ruleS_Assignment= ruleS_Assignment EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_AssignmentRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Assignment=ruleS_Assignment();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Assignment; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Assignment"


    // $ANTLR start "ruleS_Assignment"
    // InternalGaml.g:2299:1: ruleS_Assignment returns [EObject current=null] : ( ( (lv_expr_0_0= ruleExpression ) ) ( (lv_key_1_0= rule_AssignmentKey ) ) ( (lv_value_2_0= ruleExpression ) ) ( (lv_facets_3_0= ruleFacet ) )* otherlv_4= ';' ) ;
    public final EObject ruleS_Assignment() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        EObject lv_expr_0_0 = null;

        AntlrDatatypeRuleToken lv_key_1_0 = null;

        EObject lv_value_2_0 = null;

        EObject lv_facets_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2305:2: ( ( ( (lv_expr_0_0= ruleExpression ) ) ( (lv_key_1_0= rule_AssignmentKey ) ) ( (lv_value_2_0= ruleExpression ) ) ( (lv_facets_3_0= ruleFacet ) )* otherlv_4= ';' ) )
            // InternalGaml.g:2306:2: ( ( (lv_expr_0_0= ruleExpression ) ) ( (lv_key_1_0= rule_AssignmentKey ) ) ( (lv_value_2_0= ruleExpression ) ) ( (lv_facets_3_0= ruleFacet ) )* otherlv_4= ';' )
            {
            // InternalGaml.g:2306:2: ( ( (lv_expr_0_0= ruleExpression ) ) ( (lv_key_1_0= rule_AssignmentKey ) ) ( (lv_value_2_0= ruleExpression ) ) ( (lv_facets_3_0= ruleFacet ) )* otherlv_4= ';' )
            // InternalGaml.g:2307:3: ( (lv_expr_0_0= ruleExpression ) ) ( (lv_key_1_0= rule_AssignmentKey ) ) ( (lv_value_2_0= ruleExpression ) ) ( (lv_facets_3_0= ruleFacet ) )* otherlv_4= ';'
            {
            // InternalGaml.g:2307:3: ( (lv_expr_0_0= ruleExpression ) )
            // InternalGaml.g:2308:4: (lv_expr_0_0= ruleExpression )
            {
            // InternalGaml.g:2308:4: (lv_expr_0_0= ruleExpression )
            // InternalGaml.g:2309:5: lv_expr_0_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getExprExpressionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_25);
            lv_expr_0_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_0_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2326:3: ( (lv_key_1_0= rule_AssignmentKey ) )
            // InternalGaml.g:2327:4: (lv_key_1_0= rule_AssignmentKey )
            {
            // InternalGaml.g:2327:4: (lv_key_1_0= rule_AssignmentKey )
            // InternalGaml.g:2328:5: lv_key_1_0= rule_AssignmentKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getKey_AssignmentKeyParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_1_0=rule_AssignmentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_1_0,
              						"gama.core.lang.Gaml._AssignmentKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2345:3: ( (lv_value_2_0= ruleExpression ) )
            // InternalGaml.g:2346:4: (lv_value_2_0= ruleExpression )
            {
            // InternalGaml.g:2346:4: (lv_value_2_0= ruleExpression )
            // InternalGaml.g:2347:5: lv_value_2_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_AssignmentAccess().getValueExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_26);
            lv_value_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2364:3: ( (lv_facets_3_0= ruleFacet ) )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==RULE_ID||LA45_0==15||(LA45_0>=90 && LA45_0<=102)) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalGaml.g:2365:4: (lv_facets_3_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2365:4: (lv_facets_3_0= ruleFacet )
            	    // InternalGaml.g:2366:5: lv_facets_3_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_AssignmentAccess().getFacetsFacetParserRuleCall_3_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_26);
            	    lv_facets_3_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_AssignmentRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_3_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_4, grammarAccess.getS_AssignmentAccess().getSemicolonKeyword_4());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Assignment"


    // $ANTLR start "entryRuleS_Equations"
    // InternalGaml.g:2391:1: entryRuleS_Equations returns [EObject current=null] : iv_ruleS_Equations= ruleS_Equations EOF ;
    public final EObject entryRuleS_Equations() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Equations = null;


        try {
            // InternalGaml.g:2391:52: (iv_ruleS_Equations= ruleS_Equations EOF )
            // InternalGaml.g:2392:2: iv_ruleS_Equations= ruleS_Equations EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_EquationsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Equations=ruleS_Equations();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Equations; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Equations"


    // $ANTLR start "ruleS_Equations"
    // InternalGaml.g:2398:1: ruleS_Equations returns [EObject current=null] : ( ( (lv_key_0_0= rule_EquationsKey ) ) ( (lv_name_1_0= ruleValid_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' ) ) ;
    public final EObject ruleS_Equations() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_equations_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2404:2: ( ( ( (lv_key_0_0= rule_EquationsKey ) ) ( (lv_name_1_0= ruleValid_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' ) ) )
            // InternalGaml.g:2405:2: ( ( (lv_key_0_0= rule_EquationsKey ) ) ( (lv_name_1_0= ruleValid_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' ) )
            {
            // InternalGaml.g:2405:2: ( ( (lv_key_0_0= rule_EquationsKey ) ) ( (lv_name_1_0= ruleValid_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' ) )
            // InternalGaml.g:2406:3: ( (lv_key_0_0= rule_EquationsKey ) ) ( (lv_name_1_0= ruleValid_ID ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' )
            {
            // InternalGaml.g:2406:3: ( (lv_key_0_0= rule_EquationsKey ) )
            // InternalGaml.g:2407:4: (lv_key_0_0= rule_EquationsKey )
            {
            // InternalGaml.g:2407:4: (lv_key_0_0= rule_EquationsKey )
            // InternalGaml.g:2408:5: lv_key_0_0= rule_EquationsKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationsAccess().getKey_EquationsKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_EquationsKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._EquationsKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2425:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:2426:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:2426:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:2427:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationsAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2444:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==RULE_ID||LA46_0==15||(LA46_0>=90 && LA46_0<=102)) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalGaml.g:2445:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2445:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:2446:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_EquationsAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_EquationsRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            // InternalGaml.g:2463:3: ( (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' ) | otherlv_7= ';' )
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==32) ) {
                alt48=1;
            }
            else if ( (LA48_0==21) ) {
                alt48=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }
            switch (alt48) {
                case 1 :
                    // InternalGaml.g:2464:4: (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' )
                    {
                    // InternalGaml.g:2464:4: (otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}' )
                    // InternalGaml.g:2465:5: otherlv_3= '{' ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )* otherlv_6= '}'
                    {
                    otherlv_3=(Token)match(input,32,FOLLOW_27); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getS_EquationsAccess().getLeftCurlyBracketKeyword_3_0_0());
                      				
                    }
                    // InternalGaml.g:2469:5: ( ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';' )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==RULE_ID||LA47_0==36||(LA47_0>=38 && LA47_0<=81)) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // InternalGaml.g:2470:6: ( (lv_equations_4_0= ruleS_Equation ) ) otherlv_5= ';'
                    	    {
                    	    // InternalGaml.g:2470:6: ( (lv_equations_4_0= ruleS_Equation ) )
                    	    // InternalGaml.g:2471:7: (lv_equations_4_0= ruleS_Equation )
                    	    {
                    	    // InternalGaml.g:2471:7: (lv_equations_4_0= ruleS_Equation )
                    	    // InternalGaml.g:2472:8: lv_equations_4_0= ruleS_Equation
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      								newCompositeNode(grammarAccess.getS_EquationsAccess().getEquationsS_EquationParserRuleCall_3_0_1_0_0());
                    	      							
                    	    }
                    	    pushFollow(FOLLOW_21);
                    	    lv_equations_4_0=ruleS_Equation();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      								if (current==null) {
                    	      									current = createModelElementForParent(grammarAccess.getS_EquationsRule());
                    	      								}
                    	      								add(
                    	      									current,
                    	      									"equations",
                    	      									lv_equations_4_0,
                    	      									"gama.core.lang.Gaml.S_Equation");
                    	      								afterParserOrEnumRuleCall();
                    	      							
                    	    }

                    	    }


                    	    }

                    	    otherlv_5=(Token)match(input,21,FOLLOW_27); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      						newLeafNode(otherlv_5, grammarAccess.getS_EquationsAccess().getSemicolonKeyword_3_0_1_1());
                    	      					
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);

                    otherlv_6=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_6, grammarAccess.getS_EquationsAccess().getRightCurlyBracketKeyword_3_0_2());
                      				
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2500:4: otherlv_7= ';'
                    {
                    otherlv_7=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_7, grammarAccess.getS_EquationsAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Equations"


    // $ANTLR start "entryRuleS_Equation"
    // InternalGaml.g:2509:1: entryRuleS_Equation returns [EObject current=null] : iv_ruleS_Equation= ruleS_Equation EOF ;
    public final EObject entryRuleS_Equation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Equation = null;


        try {
            // InternalGaml.g:2509:51: (iv_ruleS_Equation= ruleS_Equation EOF )
            // InternalGaml.g:2510:2: iv_ruleS_Equation= ruleS_Equation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_EquationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Equation=ruleS_Equation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Equation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Equation"


    // $ANTLR start "ruleS_Equation"
    // InternalGaml.g:2516:1: ruleS_Equation returns [EObject current=null] : ( ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) ) ( (lv_key_1_0= '=' ) ) ( (lv_value_2_0= ruleExpression ) ) ) ;
    public final EObject ruleS_Equation() throws RecognitionException {
        EObject current = null;

        Token lv_key_1_0=null;
        EObject lv_expr_0_1 = null;

        EObject lv_expr_0_2 = null;

        EObject lv_value_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2522:2: ( ( ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) ) ( (lv_key_1_0= '=' ) ) ( (lv_value_2_0= ruleExpression ) ) ) )
            // InternalGaml.g:2523:2: ( ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) ) ( (lv_key_1_0= '=' ) ) ( (lv_value_2_0= ruleExpression ) ) )
            {
            // InternalGaml.g:2523:2: ( ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) ) ( (lv_key_1_0= '=' ) ) ( (lv_value_2_0= ruleExpression ) ) )
            // InternalGaml.g:2524:3: ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) ) ( (lv_key_1_0= '=' ) ) ( (lv_value_2_0= ruleExpression ) )
            {
            // InternalGaml.g:2524:3: ( ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) ) )
            // InternalGaml.g:2525:4: ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) )
            {
            // InternalGaml.g:2525:4: ( (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef ) )
            // InternalGaml.g:2526:5: (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef )
            {
            // InternalGaml.g:2526:5: (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef )
            int alt49=2;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // InternalGaml.g:2527:6: lv_expr_0_1= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_EquationAccess().getExprFunctionParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_28);
                    lv_expr_0_1=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_EquationRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_0_1,
                      							"gama.core.lang.Gaml.Function");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:2543:6: lv_expr_0_2= ruleVariableRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_EquationAccess().getExprVariableRefParserRuleCall_0_0_1());
                      					
                    }
                    pushFollow(FOLLOW_28);
                    lv_expr_0_2=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_EquationRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_0_2,
                      							"gama.core.lang.Gaml.VariableRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalGaml.g:2561:3: ( (lv_key_1_0= '=' ) )
            // InternalGaml.g:2562:4: (lv_key_1_0= '=' )
            {
            // InternalGaml.g:2562:4: (lv_key_1_0= '=' )
            // InternalGaml.g:2563:5: lv_key_1_0= '='
            {
            lv_key_1_0=(Token)match(input,34,FOLLOW_5); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_1_0, grammarAccess.getS_EquationAccess().getKeyEqualsSignKeyword_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_EquationRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_1_0, "=");
              				
            }

            }


            }

            // InternalGaml.g:2575:3: ( (lv_value_2_0= ruleExpression ) )
            // InternalGaml.g:2576:4: (lv_value_2_0= ruleExpression )
            {
            // InternalGaml.g:2576:4: (lv_value_2_0= ruleExpression )
            // InternalGaml.g:2577:5: lv_value_2_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_EquationAccess().getValueExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_value_2_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_EquationRule());
              					}
              					set(
              						current,
              						"value",
              						lv_value_2_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Equation"


    // $ANTLR start "entryRuleS_Solve"
    // InternalGaml.g:2598:1: entryRuleS_Solve returns [EObject current=null] : iv_ruleS_Solve= ruleS_Solve EOF ;
    public final EObject entryRuleS_Solve() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Solve = null;


        try {
            // InternalGaml.g:2598:48: (iv_ruleS_Solve= ruleS_Solve EOF )
            // InternalGaml.g:2599:2: iv_ruleS_Solve= ruleS_Solve EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_SolveRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Solve=ruleS_Solve();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Solve; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Solve"


    // $ANTLR start "ruleS_Solve"
    // InternalGaml.g:2605:1: ruleS_Solve returns [EObject current=null] : ( ( (lv_key_0_0= rule_SolveKey ) ) ( (lv_expr_1_0= ruleEquationRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleS_Solve() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2611:2: ( ( ( (lv_key_0_0= rule_SolveKey ) ) ( (lv_expr_1_0= ruleEquationRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:2612:2: ( ( (lv_key_0_0= rule_SolveKey ) ) ( (lv_expr_1_0= ruleEquationRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:2612:2: ( ( (lv_key_0_0= rule_SolveKey ) ) ( (lv_expr_1_0= ruleEquationRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:2613:3: ( (lv_key_0_0= rule_SolveKey ) ) ( (lv_expr_1_0= ruleEquationRef ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:2613:3: ( (lv_key_0_0= rule_SolveKey ) )
            // InternalGaml.g:2614:4: (lv_key_0_0= rule_SolveKey )
            {
            // InternalGaml.g:2614:4: (lv_key_0_0= rule_SolveKey )
            // InternalGaml.g:2615:5: lv_key_0_0= rule_SolveKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SolveAccess().getKey_SolveKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_key_0_0=rule_SolveKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SolveRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SolveKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2632:3: ( (lv_expr_1_0= ruleEquationRef ) )
            // InternalGaml.g:2633:4: (lv_expr_1_0= ruleEquationRef )
            {
            // InternalGaml.g:2633:4: (lv_expr_1_0= ruleEquationRef )
            // InternalGaml.g:2634:5: lv_expr_1_0= ruleEquationRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_SolveAccess().getExprEquationRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleEquationRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_SolveRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.EquationRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2651:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==RULE_ID||LA50_0==15||(LA50_0>=90 && LA50_0<=102)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalGaml.g:2652:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2652:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:2653:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_SolveAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_SolveRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

            // InternalGaml.g:2670:3: ( ( (lv_block_3_0= ruleBlock ) ) | otherlv_4= ';' )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==32) ) {
                alt51=1;
            }
            else if ( (LA51_0==21) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // InternalGaml.g:2671:4: ( (lv_block_3_0= ruleBlock ) )
                    {
                    // InternalGaml.g:2671:4: ( (lv_block_3_0= ruleBlock ) )
                    // InternalGaml.g:2672:5: (lv_block_3_0= ruleBlock )
                    {
                    // InternalGaml.g:2672:5: (lv_block_3_0= ruleBlock )
                    // InternalGaml.g:2673:6: lv_block_3_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_SolveAccess().getBlockBlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_SolveRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2691:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getS_SolveAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Solve"


    // $ANTLR start "entryRuleS_Display"
    // InternalGaml.g:2700:1: entryRuleS_Display returns [EObject current=null] : iv_ruleS_Display= ruleS_Display EOF ;
    public final EObject entryRuleS_Display() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleS_Display = null;


        try {
            // InternalGaml.g:2700:50: (iv_ruleS_Display= ruleS_Display EOF )
            // InternalGaml.g:2701:2: iv_ruleS_Display= ruleS_Display EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getS_DisplayRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleS_Display=ruleS_Display();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleS_Display; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleS_Display"


    // $ANTLR start "ruleS_Display"
    // InternalGaml.g:2707:1: ruleS_Display returns [EObject current=null] : ( ( (lv_key_0_0= 'display' ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleD_Block ) ) ) ;
    public final EObject ruleS_Display() throws RecognitionException {
        EObject current = null;

        Token lv_key_0_0=null;
        Token lv_name_1_2=null;
        AntlrDatatypeRuleToken lv_name_1_1 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2713:2: ( ( ( (lv_key_0_0= 'display' ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleD_Block ) ) ) )
            // InternalGaml.g:2714:2: ( ( (lv_key_0_0= 'display' ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleD_Block ) ) )
            {
            // InternalGaml.g:2714:2: ( ( (lv_key_0_0= 'display' ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleD_Block ) ) )
            // InternalGaml.g:2715:3: ( (lv_key_0_0= 'display' ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ( (lv_facets_2_0= ruleFacet ) )* ( (lv_block_3_0= ruleD_Block ) )
            {
            // InternalGaml.g:2715:3: ( (lv_key_0_0= 'display' ) )
            // InternalGaml.g:2716:4: (lv_key_0_0= 'display' )
            {
            // InternalGaml.g:2716:4: (lv_key_0_0= 'display' )
            // InternalGaml.g:2717:5: lv_key_0_0= 'display'
            {
            lv_key_0_0=(Token)match(input,35,FOLLOW_13); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_key_0_0, grammarAccess.getS_DisplayAccess().getKeyDisplayKeyword_0_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getS_DisplayRule());
              					}
              					setWithLastConsumed(current, "key", lv_key_0_0, "display");
              				
            }

            }


            }

            // InternalGaml.g:2729:3: ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) )
            // InternalGaml.g:2730:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            {
            // InternalGaml.g:2730:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            // InternalGaml.g:2731:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            {
            // InternalGaml.g:2731:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==RULE_ID||LA52_0==36||(LA52_0>=38 && LA52_0<=81)) ) {
                alt52=1;
            }
            else if ( (LA52_0==RULE_STRING) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // InternalGaml.g:2732:6: lv_name_1_1= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getS_DisplayAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_16);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getS_DisplayRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:2748:6: lv_name_1_2= RULE_STRING
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_16); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getS_DisplayAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getS_DisplayRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }

            // InternalGaml.g:2765:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==RULE_ID||LA53_0==15||(LA53_0>=90 && LA53_0<=102)) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalGaml.g:2766:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2766:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:2767:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getS_DisplayAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_16);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getS_DisplayRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            // InternalGaml.g:2784:3: ( (lv_block_3_0= ruleD_Block ) )
            // InternalGaml.g:2785:4: (lv_block_3_0= ruleD_Block )
            {
            // InternalGaml.g:2785:4: (lv_block_3_0= ruleD_Block )
            // InternalGaml.g:2786:5: lv_block_3_0= ruleD_Block
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getS_DisplayAccess().getBlockD_BlockParserRuleCall_3_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_block_3_0=ruleD_Block();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getS_DisplayRule());
              					}
              					set(
              						current,
              						"block",
              						lv_block_3_0,
              						"gama.core.lang.Gaml.D_Block");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleS_Display"


    // $ANTLR start "entryRuleD_Block"
    // InternalGaml.g:2807:1: entryRuleD_Block returns [EObject current=null] : iv_ruleD_Block= ruleD_Block EOF ;
    public final EObject entryRuleD_Block() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleD_Block = null;


        try {
            // InternalGaml.g:2807:48: (iv_ruleD_Block= ruleD_Block EOF )
            // InternalGaml.g:2808:2: iv_ruleD_Block= ruleD_Block EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_BlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Block=ruleD_Block();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Block; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleD_Block"


    // $ANTLR start "ruleD_Block"
    // InternalGaml.g:2814:1: ruleD_Block returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_statements_2_0= ruleD_Statement ) )* otherlv_3= '}' ) ;
    public final EObject ruleD_Block() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2820:2: ( ( () otherlv_1= '{' ( (lv_statements_2_0= ruleD_Statement ) )* otherlv_3= '}' ) )
            // InternalGaml.g:2821:2: ( () otherlv_1= '{' ( (lv_statements_2_0= ruleD_Statement ) )* otherlv_3= '}' )
            {
            // InternalGaml.g:2821:2: ( () otherlv_1= '{' ( (lv_statements_2_0= ruleD_Statement ) )* otherlv_3= '}' )
            // InternalGaml.g:2822:3: () otherlv_1= '{' ( (lv_statements_2_0= ruleD_Statement ) )* otherlv_3= '}'
            {
            // InternalGaml.g:2822:3: ()
            // InternalGaml.g:2823:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getD_BlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,32,FOLLOW_29); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getD_BlockAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalGaml.g:2833:3: ( (lv_statements_2_0= ruleD_Statement ) )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=38 && LA54_0<=39)||(LA54_0>=41 && LA54_0<=76)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // InternalGaml.g:2834:4: (lv_statements_2_0= ruleD_Statement )
            	    {
            	    // InternalGaml.g:2834:4: (lv_statements_2_0= ruleD_Statement )
            	    // InternalGaml.g:2835:5: lv_statements_2_0= ruleD_Statement
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getD_BlockAccess().getStatementsD_StatementParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_29);
            	    lv_statements_2_0=ruleD_Statement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getD_BlockRule());
            	      					}
            	      					add(
            	      						current,
            	      						"statements",
            	      						lv_statements_2_0,
            	      						"gama.core.lang.Gaml.D_Statement");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop54;
                }
            } while (true);

            otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getD_BlockAccess().getRightCurlyBracketKeyword_3());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleD_Block"


    // $ANTLR start "entryRuleD_Statement"
    // InternalGaml.g:2860:1: entryRuleD_Statement returns [EObject current=null] : iv_ruleD_Statement= ruleD_Statement EOF ;
    public final EObject entryRuleD_Statement() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleD_Statement = null;


        try {
            // InternalGaml.g:2860:52: (iv_ruleD_Statement= ruleD_Statement EOF )
            // InternalGaml.g:2861:2: iv_ruleD_Statement= ruleD_Statement EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_StatementRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Statement=ruleD_Statement();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Statement; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleD_Statement"


    // $ANTLR start "ruleD_Statement"
    // InternalGaml.g:2867:1: ruleD_Statement returns [EObject current=null] : (this_D_Species_0= ruleD_Species | this_S_1Expr_Facets_BlockOrEnd_1= ruleS_1Expr_Facets_BlockOrEnd ) ;
    public final EObject ruleD_Statement() throws RecognitionException {
        EObject current = null;

        EObject this_D_Species_0 = null;

        EObject this_S_1Expr_Facets_BlockOrEnd_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:2873:2: ( (this_D_Species_0= ruleD_Species | this_S_1Expr_Facets_BlockOrEnd_1= ruleS_1Expr_Facets_BlockOrEnd ) )
            // InternalGaml.g:2874:2: (this_D_Species_0= ruleD_Species | this_S_1Expr_Facets_BlockOrEnd_1= ruleS_1Expr_Facets_BlockOrEnd )
            {
            // InternalGaml.g:2874:2: (this_D_Species_0= ruleD_Species | this_S_1Expr_Facets_BlockOrEnd_1= ruleS_1Expr_Facets_BlockOrEnd )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=38 && LA55_0<=39)) ) {
                alt55=1;
            }
            else if ( ((LA55_0>=41 && LA55_0<=76)) ) {
                alt55=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // InternalGaml.g:2875:3: this_D_Species_0= ruleD_Species
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getD_StatementAccess().getD_SpeciesParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_D_Species_0=ruleD_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_D_Species_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:2884:3: this_S_1Expr_Facets_BlockOrEnd_1= ruleS_1Expr_Facets_BlockOrEnd
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getD_StatementAccess().getS_1Expr_Facets_BlockOrEndParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_1Expr_Facets_BlockOrEnd_1=ruleS_1Expr_Facets_BlockOrEnd();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_1Expr_Facets_BlockOrEnd_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleD_Statement"


    // $ANTLR start "entryRuleD_Species"
    // InternalGaml.g:2896:1: entryRuleD_Species returns [EObject current=null] : iv_ruleD_Species= ruleD_Species EOF ;
    public final EObject entryRuleD_Species() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleD_Species = null;


        try {
            // InternalGaml.g:2896:50: (iv_ruleD_Species= ruleD_Species EOF )
            // InternalGaml.g:2897:2: iv_ruleD_Species= ruleD_Species EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getD_SpeciesRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleD_Species=ruleD_Species();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleD_Species; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleD_Species"


    // $ANTLR start "ruleD_Species"
    // InternalGaml.g:2903:1: ruleD_Species returns [EObject current=null] : ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' ) ) ;
    public final EObject ruleD_Species() throws RecognitionException {
        EObject current = null;

        Token otherlv_4=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_facets_2_0 = null;

        EObject lv_block_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:2909:2: ( ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' ) ) )
            // InternalGaml.g:2910:2: ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' ) )
            {
            // InternalGaml.g:2910:2: ( ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' ) )
            // InternalGaml.g:2911:3: ( (lv_key_0_0= rule_SpeciesKey ) ) ( (lv_expr_1_0= ruleExpression ) ) ( (lv_facets_2_0= ruleFacet ) )* ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' )
            {
            // InternalGaml.g:2911:3: ( (lv_key_0_0= rule_SpeciesKey ) )
            // InternalGaml.g:2912:4: (lv_key_0_0= rule_SpeciesKey )
            {
            // InternalGaml.g:2912:4: (lv_key_0_0= rule_SpeciesKey )
            // InternalGaml.g:2913:5: lv_key_0_0= rule_SpeciesKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getD_SpeciesAccess().getKey_SpeciesKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_5);
            lv_key_0_0=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml._SpeciesKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2930:3: ( (lv_expr_1_0= ruleExpression ) )
            // InternalGaml.g:2931:4: (lv_expr_1_0= ruleExpression )
            {
            // InternalGaml.g:2931:4: (lv_expr_1_0= ruleExpression )
            // InternalGaml.g:2932:5: lv_expr_1_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getD_SpeciesAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_15);
            lv_expr_1_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_1_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:2949:3: ( (lv_facets_2_0= ruleFacet ) )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==RULE_ID||LA56_0==15||(LA56_0>=90 && LA56_0<=102)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // InternalGaml.g:2950:4: (lv_facets_2_0= ruleFacet )
            	    {
            	    // InternalGaml.g:2950:4: (lv_facets_2_0= ruleFacet )
            	    // InternalGaml.g:2951:5: lv_facets_2_0= ruleFacet
            	    {
            	    if ( state.backtracking==0 ) {

            	      					newCompositeNode(grammarAccess.getD_SpeciesAccess().getFacetsFacetParserRuleCall_2_0());
            	      				
            	    }
            	    pushFollow(FOLLOW_15);
            	    lv_facets_2_0=ruleFacet();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      					if (current==null) {
            	      						current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
            	      					}
            	      					add(
            	      						current,
            	      						"facets",
            	      						lv_facets_2_0,
            	      						"gama.core.lang.Gaml.Facet");
            	      					afterParserOrEnumRuleCall();
            	      				
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            // InternalGaml.g:2968:3: ( ( (lv_block_3_0= ruleD_Block ) ) | otherlv_4= ';' )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==32) ) {
                alt57=1;
            }
            else if ( (LA57_0==21) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // InternalGaml.g:2969:4: ( (lv_block_3_0= ruleD_Block ) )
                    {
                    // InternalGaml.g:2969:4: ( (lv_block_3_0= ruleD_Block ) )
                    // InternalGaml.g:2970:5: (lv_block_3_0= ruleD_Block )
                    {
                    // InternalGaml.g:2970:5: (lv_block_3_0= ruleD_Block )
                    // InternalGaml.g:2971:6: lv_block_3_0= ruleD_Block
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getD_SpeciesAccess().getBlockD_BlockParserRuleCall_3_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_3_0=ruleD_Block();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getD_SpeciesRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_3_0,
                      							"gama.core.lang.Gaml.D_Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:2989:4: otherlv_4= ';'
                    {
                    otherlv_4=(Token)match(input,21,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getD_SpeciesAccess().getSemicolonKeyword_3_1());
                      			
                    }

                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleD_Species"


    // $ANTLR start "entryRule_EquationsKey"
    // InternalGaml.g:2998:1: entryRule_EquationsKey returns [String current=null] : iv_rule_EquationsKey= rule_EquationsKey EOF ;
    public final String entryRule_EquationsKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_EquationsKey = null;


        try {
            // InternalGaml.g:2998:53: (iv_rule_EquationsKey= rule_EquationsKey EOF )
            // InternalGaml.g:2999:2: iv_rule_EquationsKey= rule_EquationsKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_EquationsKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_EquationsKey=rule_EquationsKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_EquationsKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_EquationsKey"


    // $ANTLR start "rule_EquationsKey"
    // InternalGaml.g:3005:1: rule_EquationsKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'equation' ;
    public final AntlrDatatypeRuleToken rule_EquationsKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3011:2: (kw= 'equation' )
            // InternalGaml.g:3012:2: kw= 'equation'
            {
            kw=(Token)match(input,36,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_EquationsKeyAccess().getEquationKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_EquationsKey"


    // $ANTLR start "entryRule_SolveKey"
    // InternalGaml.g:3020:1: entryRule_SolveKey returns [String current=null] : iv_rule_SolveKey= rule_SolveKey EOF ;
    public final String entryRule_SolveKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_SolveKey = null;


        try {
            // InternalGaml.g:3020:49: (iv_rule_SolveKey= rule_SolveKey EOF )
            // InternalGaml.g:3021:2: iv_rule_SolveKey= rule_SolveKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_SolveKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_SolveKey=rule_SolveKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_SolveKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_SolveKey"


    // $ANTLR start "rule_SolveKey"
    // InternalGaml.g:3027:1: rule_SolveKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'solve' ;
    public final AntlrDatatypeRuleToken rule_SolveKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3033:2: (kw= 'solve' )
            // InternalGaml.g:3034:2: kw= 'solve'
            {
            kw=(Token)match(input,37,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_SolveKeyAccess().getSolveKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_SolveKey"


    // $ANTLR start "entryRule_SpeciesKey"
    // InternalGaml.g:3042:1: entryRule_SpeciesKey returns [String current=null] : iv_rule_SpeciesKey= rule_SpeciesKey EOF ;
    public final String entryRule_SpeciesKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_SpeciesKey = null;


        try {
            // InternalGaml.g:3042:51: (iv_rule_SpeciesKey= rule_SpeciesKey EOF )
            // InternalGaml.g:3043:2: iv_rule_SpeciesKey= rule_SpeciesKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_SpeciesKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_SpeciesKey=rule_SpeciesKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_SpeciesKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_SpeciesKey"


    // $ANTLR start "rule_SpeciesKey"
    // InternalGaml.g:3049:1: rule_SpeciesKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'species' | kw= 'grid' ) ;
    public final AntlrDatatypeRuleToken rule_SpeciesKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3055:2: ( (kw= 'species' | kw= 'grid' ) )
            // InternalGaml.g:3056:2: (kw= 'species' | kw= 'grid' )
            {
            // InternalGaml.g:3056:2: (kw= 'species' | kw= 'grid' )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==38) ) {
                alt58=1;
            }
            else if ( (LA58_0==39) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // InternalGaml.g:3057:3: kw= 'species'
                    {
                    kw=(Token)match(input,38,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_SpeciesKeyAccess().getSpeciesKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3063:3: kw= 'grid'
                    {
                    kw=(Token)match(input,39,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_SpeciesKeyAccess().getGridKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_SpeciesKey"


    // $ANTLR start "entryRule_ExperimentKey"
    // InternalGaml.g:3072:1: entryRule_ExperimentKey returns [String current=null] : iv_rule_ExperimentKey= rule_ExperimentKey EOF ;
    public final String entryRule_ExperimentKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_ExperimentKey = null;


        try {
            // InternalGaml.g:3072:54: (iv_rule_ExperimentKey= rule_ExperimentKey EOF )
            // InternalGaml.g:3073:2: iv_rule_ExperimentKey= rule_ExperimentKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_ExperimentKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_ExperimentKey=rule_ExperimentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_ExperimentKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_ExperimentKey"


    // $ANTLR start "rule_ExperimentKey"
    // InternalGaml.g:3079:1: rule_ExperimentKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'experiment' ;
    public final AntlrDatatypeRuleToken rule_ExperimentKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3085:2: (kw= 'experiment' )
            // InternalGaml.g:3086:2: kw= 'experiment'
            {
            kw=(Token)match(input,40,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.get_ExperimentKeyAccess().getExperimentKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_ExperimentKey"


    // $ANTLR start "entryRule_1Expr_Facets_BlockOrEnd_Key"
    // InternalGaml.g:3094:1: entryRule_1Expr_Facets_BlockOrEnd_Key returns [String current=null] : iv_rule_1Expr_Facets_BlockOrEnd_Key= rule_1Expr_Facets_BlockOrEnd_Key EOF ;
    public final String entryRule_1Expr_Facets_BlockOrEnd_Key() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_1Expr_Facets_BlockOrEnd_Key = null;


        try {
            // InternalGaml.g:3094:68: (iv_rule_1Expr_Facets_BlockOrEnd_Key= rule_1Expr_Facets_BlockOrEnd_Key EOF )
            // InternalGaml.g:3095:2: iv_rule_1Expr_Facets_BlockOrEnd_Key= rule_1Expr_Facets_BlockOrEnd_Key EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_1Expr_Facets_BlockOrEnd_Key=rule_1Expr_Facets_BlockOrEnd_Key();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_1Expr_Facets_BlockOrEnd_Key.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_1Expr_Facets_BlockOrEnd_Key"


    // $ANTLR start "rule_1Expr_Facets_BlockOrEnd_Key"
    // InternalGaml.g:3101:1: rule_1Expr_Facets_BlockOrEnd_Key returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this__LayerKey_0= rule_LayerKey | kw= 'ask' | kw= 'release' | kw= 'capture' | kw= 'create' | kw= 'write' | kw= 'error' | kw= 'warn' | kw= 'exception' | kw= 'save' | kw= 'assert' | kw= 'inspect' | kw= 'browse' | kw= 'draw' | kw= 'using' | kw= 'switch' | kw= 'put' | kw= 'add' | kw= 'remove' | kw= 'match' | kw= 'match_between' | kw= 'match_one' | kw= 'parameter' | kw= 'status' | kw= 'highlight' | kw= 'focus_on' | kw= 'layout' ) ;
    public final AntlrDatatypeRuleToken rule_1Expr_Facets_BlockOrEnd_Key() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        AntlrDatatypeRuleToken this__LayerKey_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:3107:2: ( (this__LayerKey_0= rule_LayerKey | kw= 'ask' | kw= 'release' | kw= 'capture' | kw= 'create' | kw= 'write' | kw= 'error' | kw= 'warn' | kw= 'exception' | kw= 'save' | kw= 'assert' | kw= 'inspect' | kw= 'browse' | kw= 'draw' | kw= 'using' | kw= 'switch' | kw= 'put' | kw= 'add' | kw= 'remove' | kw= 'match' | kw= 'match_between' | kw= 'match_one' | kw= 'parameter' | kw= 'status' | kw= 'highlight' | kw= 'focus_on' | kw= 'layout' ) )
            // InternalGaml.g:3108:2: (this__LayerKey_0= rule_LayerKey | kw= 'ask' | kw= 'release' | kw= 'capture' | kw= 'create' | kw= 'write' | kw= 'error' | kw= 'warn' | kw= 'exception' | kw= 'save' | kw= 'assert' | kw= 'inspect' | kw= 'browse' | kw= 'draw' | kw= 'using' | kw= 'switch' | kw= 'put' | kw= 'add' | kw= 'remove' | kw= 'match' | kw= 'match_between' | kw= 'match_one' | kw= 'parameter' | kw= 'status' | kw= 'highlight' | kw= 'focus_on' | kw= 'layout' )
            {
            // InternalGaml.g:3108:2: (this__LayerKey_0= rule_LayerKey | kw= 'ask' | kw= 'release' | kw= 'capture' | kw= 'create' | kw= 'write' | kw= 'error' | kw= 'warn' | kw= 'exception' | kw= 'save' | kw= 'assert' | kw= 'inspect' | kw= 'browse' | kw= 'draw' | kw= 'using' | kw= 'switch' | kw= 'put' | kw= 'add' | kw= 'remove' | kw= 'match' | kw= 'match_between' | kw= 'match_one' | kw= 'parameter' | kw= 'status' | kw= 'highlight' | kw= 'focus_on' | kw= 'layout' )
            int alt59=27;
            switch ( input.LA(1) ) {
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
                {
                alt59=1;
                }
                break;
            case 41:
                {
                alt59=2;
                }
                break;
            case 42:
                {
                alt59=3;
                }
                break;
            case 43:
                {
                alt59=4;
                }
                break;
            case 44:
                {
                alt59=5;
                }
                break;
            case 45:
                {
                alt59=6;
                }
                break;
            case 46:
                {
                alt59=7;
                }
                break;
            case 47:
                {
                alt59=8;
                }
                break;
            case 48:
                {
                alt59=9;
                }
                break;
            case 49:
                {
                alt59=10;
                }
                break;
            case 50:
                {
                alt59=11;
                }
                break;
            case 51:
                {
                alt59=12;
                }
                break;
            case 52:
                {
                alt59=13;
                }
                break;
            case 53:
                {
                alt59=14;
                }
                break;
            case 54:
                {
                alt59=15;
                }
                break;
            case 55:
                {
                alt59=16;
                }
                break;
            case 56:
                {
                alt59=17;
                }
                break;
            case 57:
                {
                alt59=18;
                }
                break;
            case 58:
                {
                alt59=19;
                }
                break;
            case 59:
                {
                alt59=20;
                }
                break;
            case 60:
                {
                alt59=21;
                }
                break;
            case 61:
                {
                alt59=22;
                }
                break;
            case 62:
                {
                alt59=23;
                }
                break;
            case 63:
                {
                alt59=24;
                }
                break;
            case 64:
                {
                alt59=25;
                }
                break;
            case 65:
                {
                alt59=26;
                }
                break;
            case 66:
                {
                alt59=27;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // InternalGaml.g:3109:3: this__LayerKey_0= rule_LayerKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().get_LayerKeyParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__LayerKey_0=rule_LayerKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__LayerKey_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3120:3: kw= 'ask'
                    {
                    kw=(Token)match(input,41,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAskKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3126:3: kw= 'release'
                    {
                    kw=(Token)match(input,42,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getReleaseKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:3132:3: kw= 'capture'
                    {
                    kw=(Token)match(input,43,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getCaptureKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:3138:3: kw= 'create'
                    {
                    kw=(Token)match(input,44,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getCreateKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGaml.g:3144:3: kw= 'write'
                    {
                    kw=(Token)match(input,45,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getWriteKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGaml.g:3150:3: kw= 'error'
                    {
                    kw=(Token)match(input,46,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getErrorKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGaml.g:3156:3: kw= 'warn'
                    {
                    kw=(Token)match(input,47,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getWarnKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGaml.g:3162:3: kw= 'exception'
                    {
                    kw=(Token)match(input,48,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getExceptionKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGaml.g:3168:3: kw= 'save'
                    {
                    kw=(Token)match(input,49,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getSaveKeyword_9());
                      		
                    }

                    }
                    break;
                case 11 :
                    // InternalGaml.g:3174:3: kw= 'assert'
                    {
                    kw=(Token)match(input,50,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAssertKeyword_10());
                      		
                    }

                    }
                    break;
                case 12 :
                    // InternalGaml.g:3180:3: kw= 'inspect'
                    {
                    kw=(Token)match(input,51,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getInspectKeyword_11());
                      		
                    }

                    }
                    break;
                case 13 :
                    // InternalGaml.g:3186:3: kw= 'browse'
                    {
                    kw=(Token)match(input,52,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getBrowseKeyword_12());
                      		
                    }

                    }
                    break;
                case 14 :
                    // InternalGaml.g:3192:3: kw= 'draw'
                    {
                    kw=(Token)match(input,53,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getDrawKeyword_13());
                      		
                    }

                    }
                    break;
                case 15 :
                    // InternalGaml.g:3198:3: kw= 'using'
                    {
                    kw=(Token)match(input,54,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getUsingKeyword_14());
                      		
                    }

                    }
                    break;
                case 16 :
                    // InternalGaml.g:3204:3: kw= 'switch'
                    {
                    kw=(Token)match(input,55,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getSwitchKeyword_15());
                      		
                    }

                    }
                    break;
                case 17 :
                    // InternalGaml.g:3210:3: kw= 'put'
                    {
                    kw=(Token)match(input,56,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getPutKeyword_16());
                      		
                    }

                    }
                    break;
                case 18 :
                    // InternalGaml.g:3216:3: kw= 'add'
                    {
                    kw=(Token)match(input,57,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getAddKeyword_17());
                      		
                    }

                    }
                    break;
                case 19 :
                    // InternalGaml.g:3222:3: kw= 'remove'
                    {
                    kw=(Token)match(input,58,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getRemoveKeyword_18());
                      		
                    }

                    }
                    break;
                case 20 :
                    // InternalGaml.g:3228:3: kw= 'match'
                    {
                    kw=(Token)match(input,59,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatchKeyword_19());
                      		
                    }

                    }
                    break;
                case 21 :
                    // InternalGaml.g:3234:3: kw= 'match_between'
                    {
                    kw=(Token)match(input,60,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatch_betweenKeyword_20());
                      		
                    }

                    }
                    break;
                case 22 :
                    // InternalGaml.g:3240:3: kw= 'match_one'
                    {
                    kw=(Token)match(input,61,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getMatch_oneKeyword_21());
                      		
                    }

                    }
                    break;
                case 23 :
                    // InternalGaml.g:3246:3: kw= 'parameter'
                    {
                    kw=(Token)match(input,62,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getParameterKeyword_22());
                      		
                    }

                    }
                    break;
                case 24 :
                    // InternalGaml.g:3252:3: kw= 'status'
                    {
                    kw=(Token)match(input,63,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getStatusKeyword_23());
                      		
                    }

                    }
                    break;
                case 25 :
                    // InternalGaml.g:3258:3: kw= 'highlight'
                    {
                    kw=(Token)match(input,64,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getHighlightKeyword_24());
                      		
                    }

                    }
                    break;
                case 26 :
                    // InternalGaml.g:3264:3: kw= 'focus_on'
                    {
                    kw=(Token)match(input,65,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getFocus_onKeyword_25());
                      		
                    }

                    }
                    break;
                case 27 :
                    // InternalGaml.g:3270:3: kw= 'layout'
                    {
                    kw=(Token)match(input,66,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_1Expr_Facets_BlockOrEnd_KeyAccess().getLayoutKeyword_26());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_1Expr_Facets_BlockOrEnd_Key"


    // $ANTLR start "entryRule_LayerKey"
    // InternalGaml.g:3279:1: entryRule_LayerKey returns [String current=null] : iv_rule_LayerKey= rule_LayerKey EOF ;
    public final String entryRule_LayerKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_LayerKey = null;


        try {
            // InternalGaml.g:3279:49: (iv_rule_LayerKey= rule_LayerKey EOF )
            // InternalGaml.g:3280:2: iv_rule_LayerKey= rule_LayerKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_LayerKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_LayerKey=rule_LayerKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_LayerKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_LayerKey"


    // $ANTLR start "rule_LayerKey"
    // InternalGaml.g:3286:1: rule_LayerKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'light' | kw= 'camera' | kw= 'image' | kw= 'data' | kw= 'chart' | kw= 'agents' | kw= 'graphics' | kw= 'event' | kw= 'overlay' | kw= 'datalist' ) ;
    public final AntlrDatatypeRuleToken rule_LayerKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3292:2: ( (kw= 'light' | kw= 'camera' | kw= 'image' | kw= 'data' | kw= 'chart' | kw= 'agents' | kw= 'graphics' | kw= 'event' | kw= 'overlay' | kw= 'datalist' ) )
            // InternalGaml.g:3293:2: (kw= 'light' | kw= 'camera' | kw= 'image' | kw= 'data' | kw= 'chart' | kw= 'agents' | kw= 'graphics' | kw= 'event' | kw= 'overlay' | kw= 'datalist' )
            {
            // InternalGaml.g:3293:2: (kw= 'light' | kw= 'camera' | kw= 'image' | kw= 'data' | kw= 'chart' | kw= 'agents' | kw= 'graphics' | kw= 'event' | kw= 'overlay' | kw= 'datalist' )
            int alt60=10;
            switch ( input.LA(1) ) {
            case 67:
                {
                alt60=1;
                }
                break;
            case 68:
                {
                alt60=2;
                }
                break;
            case 69:
                {
                alt60=3;
                }
                break;
            case 70:
                {
                alt60=4;
                }
                break;
            case 71:
                {
                alt60=5;
                }
                break;
            case 72:
                {
                alt60=6;
                }
                break;
            case 73:
                {
                alt60=7;
                }
                break;
            case 74:
                {
                alt60=8;
                }
                break;
            case 75:
                {
                alt60=9;
                }
                break;
            case 76:
                {
                alt60=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }

            switch (alt60) {
                case 1 :
                    // InternalGaml.g:3294:3: kw= 'light'
                    {
                    kw=(Token)match(input,67,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getLightKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3300:3: kw= 'camera'
                    {
                    kw=(Token)match(input,68,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getCameraKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3306:3: kw= 'image'
                    {
                    kw=(Token)match(input,69,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getImageKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:3312:3: kw= 'data'
                    {
                    kw=(Token)match(input,70,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getDataKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:3318:3: kw= 'chart'
                    {
                    kw=(Token)match(input,71,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getChartKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGaml.g:3324:3: kw= 'agents'
                    {
                    kw=(Token)match(input,72,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getAgentsKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGaml.g:3330:3: kw= 'graphics'
                    {
                    kw=(Token)match(input,73,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getGraphicsKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGaml.g:3336:3: kw= 'event'
                    {
                    kw=(Token)match(input,74,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getEventKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGaml.g:3342:3: kw= 'overlay'
                    {
                    kw=(Token)match(input,75,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getOverlayKeyword_8());
                      		
                    }

                    }
                    break;
                case 10 :
                    // InternalGaml.g:3348:3: kw= 'datalist'
                    {
                    kw=(Token)match(input,76,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_LayerKeyAccess().getDatalistKeyword_9());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_LayerKey"


    // $ANTLR start "entryRule_DoKey"
    // InternalGaml.g:3357:1: entryRule_DoKey returns [String current=null] : iv_rule_DoKey= rule_DoKey EOF ;
    public final String entryRule_DoKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_DoKey = null;


        try {
            // InternalGaml.g:3357:46: (iv_rule_DoKey= rule_DoKey EOF )
            // InternalGaml.g:3358:2: iv_rule_DoKey= rule_DoKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_DoKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_DoKey=rule_DoKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_DoKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_DoKey"


    // $ANTLR start "rule_DoKey"
    // InternalGaml.g:3364:1: rule_DoKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'do' | kw= 'invoke' ) ;
    public final AntlrDatatypeRuleToken rule_DoKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3370:2: ( (kw= 'do' | kw= 'invoke' ) )
            // InternalGaml.g:3371:2: (kw= 'do' | kw= 'invoke' )
            {
            // InternalGaml.g:3371:2: (kw= 'do' | kw= 'invoke' )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==77) ) {
                alt61=1;
            }
            else if ( (LA61_0==78) ) {
                alt61=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // InternalGaml.g:3372:3: kw= 'do'
                    {
                    kw=(Token)match(input,77,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_DoKeyAccess().getDoKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3378:3: kw= 'invoke'
                    {
                    kw=(Token)match(input,78,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_DoKeyAccess().getInvokeKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_DoKey"


    // $ANTLR start "entryRule_ReflexKey"
    // InternalGaml.g:3387:1: entryRule_ReflexKey returns [String current=null] : iv_rule_ReflexKey= rule_ReflexKey EOF ;
    public final String entryRule_ReflexKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_ReflexKey = null;


        try {
            // InternalGaml.g:3387:50: (iv_rule_ReflexKey= rule_ReflexKey EOF )
            // InternalGaml.g:3388:2: iv_rule_ReflexKey= rule_ReflexKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_ReflexKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_ReflexKey=rule_ReflexKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_ReflexKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_ReflexKey"


    // $ANTLR start "rule_ReflexKey"
    // InternalGaml.g:3394:1: rule_ReflexKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'init' | kw= 'reflex' | kw= 'aspect' ) ;
    public final AntlrDatatypeRuleToken rule_ReflexKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3400:2: ( (kw= 'init' | kw= 'reflex' | kw= 'aspect' ) )
            // InternalGaml.g:3401:2: (kw= 'init' | kw= 'reflex' | kw= 'aspect' )
            {
            // InternalGaml.g:3401:2: (kw= 'init' | kw= 'reflex' | kw= 'aspect' )
            int alt62=3;
            switch ( input.LA(1) ) {
            case 79:
                {
                alt62=1;
                }
                break;
            case 80:
                {
                alt62=2;
                }
                break;
            case 81:
                {
                alt62=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // InternalGaml.g:3402:3: kw= 'init'
                    {
                    kw=(Token)match(input,79,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getInitKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3408:3: kw= 'reflex'
                    {
                    kw=(Token)match(input,80,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getReflexKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3414:3: kw= 'aspect'
                    {
                    kw=(Token)match(input,81,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_ReflexKeyAccess().getAspectKeyword_2());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_ReflexKey"


    // $ANTLR start "entryRule_AssignmentKey"
    // InternalGaml.g:3423:1: entryRule_AssignmentKey returns [String current=null] : iv_rule_AssignmentKey= rule_AssignmentKey EOF ;
    public final String entryRule_AssignmentKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_rule_AssignmentKey = null;


        try {
            // InternalGaml.g:3423:54: (iv_rule_AssignmentKey= rule_AssignmentKey EOF )
            // InternalGaml.g:3424:2: iv_rule_AssignmentKey= rule_AssignmentKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.get_AssignmentKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rule_AssignmentKey=rule_AssignmentKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rule_AssignmentKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRule_AssignmentKey"


    // $ANTLR start "rule_AssignmentKey"
    // InternalGaml.g:3430:1: rule_AssignmentKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' ) ;
    public final AntlrDatatypeRuleToken rule_AssignmentKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3436:2: ( (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' ) )
            // InternalGaml.g:3437:2: (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' )
            {
            // InternalGaml.g:3437:2: (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' )
            int alt63=8;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    // InternalGaml.g:3438:3: kw= '<-'
                    {
                    kw=(Token)match(input,15,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignHyphenMinusKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3444:3: kw= '<<'
                    {
                    kw=(Token)match(input,82,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignLessThanSignKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3450:3: (kw= '>' kw= '>' )
                    {
                    // InternalGaml.g:3450:3: (kw= '>' kw= '>' )
                    // InternalGaml.g:3451:4: kw= '>' kw= '>'
                    {
                    kw=(Token)match(input,83,FOLLOW_30); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_2_0());
                      			
                    }
                    kw=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_2_1());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalGaml.g:3463:3: kw= '<<+'
                    {
                    kw=(Token)match(input,84,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignLessThanSignPlusSignKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:3469:3: (kw= '>' kw= '>-' )
                    {
                    // InternalGaml.g:3469:3: (kw= '>' kw= '>-' )
                    // InternalGaml.g:3470:4: kw= '>' kw= '>-'
                    {
                    kw=(Token)match(input,83,FOLLOW_31); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignKeyword_4_0());
                      			
                    }
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current.merge(kw);
                      				newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignHyphenMinusKeyword_4_1());
                      			
                    }

                    }


                    }
                    break;
                case 6 :
                    // InternalGaml.g:3482:3: kw= '+<-'
                    {
                    kw=(Token)match(input,86,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getPlusSignLessThanSignHyphenMinusKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGaml.g:3488:3: kw= '<+'
                    {
                    kw=(Token)match(input,87,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getLessThanSignPlusSignKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGaml.g:3494:3: kw= '>-'
                    {
                    kw=(Token)match(input,85,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.get_AssignmentKeyAccess().getGreaterThanSignHyphenMinusKeyword_7());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rule_AssignmentKey"


    // $ANTLR start "entryRuleActionArguments"
    // InternalGaml.g:3503:1: entryRuleActionArguments returns [EObject current=null] : iv_ruleActionArguments= ruleActionArguments EOF ;
    public final EObject entryRuleActionArguments() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionArguments = null;


        try {
            // InternalGaml.g:3503:56: (iv_ruleActionArguments= ruleActionArguments EOF )
            // InternalGaml.g:3504:2: iv_ruleActionArguments= ruleActionArguments EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionArgumentsRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionArguments=ruleActionArguments();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionArguments; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionArguments"


    // $ANTLR start "ruleActionArguments"
    // InternalGaml.g:3510:1: ruleActionArguments returns [EObject current=null] : ( ( (lv_args_0_0= ruleArgumentDefinition ) ) (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )* ) ;
    public final EObject ruleActionArguments() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_args_0_0 = null;

        EObject lv_args_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:3516:2: ( ( ( (lv_args_0_0= ruleArgumentDefinition ) ) (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )* ) )
            // InternalGaml.g:3517:2: ( ( (lv_args_0_0= ruleArgumentDefinition ) ) (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )* )
            {
            // InternalGaml.g:3517:2: ( ( (lv_args_0_0= ruleArgumentDefinition ) ) (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )* )
            // InternalGaml.g:3518:3: ( (lv_args_0_0= ruleArgumentDefinition ) ) (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )*
            {
            // InternalGaml.g:3518:3: ( (lv_args_0_0= ruleArgumentDefinition ) )
            // InternalGaml.g:3519:4: (lv_args_0_0= ruleArgumentDefinition )
            {
            // InternalGaml.g:3519:4: (lv_args_0_0= ruleArgumentDefinition )
            // InternalGaml.g:3520:5: lv_args_0_0= ruleArgumentDefinition
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionArgumentsAccess().getArgsArgumentDefinitionParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_32);
            lv_args_0_0=ruleArgumentDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getActionArgumentsRule());
              					}
              					add(
              						current,
              						"args",
              						lv_args_0_0,
              						"gama.core.lang.Gaml.ArgumentDefinition");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:3537:3: (otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) ) )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==88) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // InternalGaml.g:3538:4: otherlv_1= ',' ( (lv_args_2_0= ruleArgumentDefinition ) )
            	    {
            	    otherlv_1=(Token)match(input,88,FOLLOW_23); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      				newLeafNode(otherlv_1, grammarAccess.getActionArgumentsAccess().getCommaKeyword_1_0());
            	      			
            	    }
            	    // InternalGaml.g:3542:4: ( (lv_args_2_0= ruleArgumentDefinition ) )
            	    // InternalGaml.g:3543:5: (lv_args_2_0= ruleArgumentDefinition )
            	    {
            	    // InternalGaml.g:3543:5: (lv_args_2_0= ruleArgumentDefinition )
            	    // InternalGaml.g:3544:6: lv_args_2_0= ruleArgumentDefinition
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getActionArgumentsAccess().getArgsArgumentDefinitionParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_32);
            	    lv_args_2_0=ruleArgumentDefinition();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getActionArgumentsRule());
            	      						}
            	      						add(
            	      							current,
            	      							"args",
            	      							lv_args_2_0,
            	      							"gama.core.lang.Gaml.ArgumentDefinition");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionArguments"


    // $ANTLR start "entryRuleArgumentDefinition"
    // InternalGaml.g:3566:1: entryRuleArgumentDefinition returns [EObject current=null] : iv_ruleArgumentDefinition= ruleArgumentDefinition EOF ;
    public final EObject entryRuleArgumentDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentDefinition = null;


        try {
            // InternalGaml.g:3566:59: (iv_ruleArgumentDefinition= ruleArgumentDefinition EOF )
            // InternalGaml.g:3567:2: iv_ruleArgumentDefinition= ruleArgumentDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgumentDefinition=ruleArgumentDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgumentDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArgumentDefinition"


    // $ANTLR start "ruleArgumentDefinition"
    // InternalGaml.g:3573:1: ruleArgumentDefinition returns [EObject current=null] : ( ( (lv_type_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )? ) ;
    public final EObject ruleArgumentDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        EObject lv_type_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_0 = null;

        EObject lv_default_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:3579:2: ( ( ( (lv_type_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )? ) )
            // InternalGaml.g:3580:2: ( ( (lv_type_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )? )
            {
            // InternalGaml.g:3580:2: ( ( (lv_type_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )? )
            // InternalGaml.g:3581:3: ( (lv_type_0_0= ruleTypeRef ) ) ( (lv_name_1_0= ruleValid_ID ) ) (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )?
            {
            // InternalGaml.g:3581:3: ( (lv_type_0_0= ruleTypeRef ) )
            // InternalGaml.g:3582:4: (lv_type_0_0= ruleTypeRef )
            {
            // InternalGaml.g:3582:4: (lv_type_0_0= ruleTypeRef )
            // InternalGaml.g:3583:5: lv_type_0_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getTypeTypeRefParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_12);
            lv_type_0_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
              					}
              					set(
              						current,
              						"type",
              						lv_type_0_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:3600:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:3601:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:3601:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:3602:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_33);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:3619:3: (otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==15) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalGaml.g:3620:4: otherlv_2= '<-' ( (lv_default_3_0= ruleExpression ) )
                    {
                    otherlv_2=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getArgumentDefinitionAccess().getLessThanSignHyphenMinusKeyword_2_0());
                      			
                    }
                    // InternalGaml.g:3624:4: ( (lv_default_3_0= ruleExpression ) )
                    // InternalGaml.g:3625:5: (lv_default_3_0= ruleExpression )
                    {
                    // InternalGaml.g:3625:5: (lv_default_3_0= ruleExpression )
                    // InternalGaml.g:3626:6: lv_default_3_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getArgumentDefinitionAccess().getDefaultExpressionParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_default_3_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getArgumentDefinitionRule());
                      						}
                      						set(
                      							current,
                      							"default",
                      							lv_default_3_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArgumentDefinition"


    // $ANTLR start "entryRuleFacet"
    // InternalGaml.g:3648:1: entryRuleFacet returns [EObject current=null] : iv_ruleFacet= ruleFacet EOF ;
    public final EObject entryRuleFacet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFacet = null;


        try {
            // InternalGaml.g:3648:46: (iv_ruleFacet= ruleFacet EOF )
            // InternalGaml.g:3649:2: iv_ruleFacet= ruleFacet EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFacet=ruleFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFacet"


    // $ANTLR start "ruleFacet"
    // InternalGaml.g:3655:1: ruleFacet returns [EObject current=null] : (this_ActionFacet_0= ruleActionFacet | this_DefinitionFacet_1= ruleDefinitionFacet | this_ClassicFacet_2= ruleClassicFacet ) ;
    public final EObject ruleFacet() throws RecognitionException {
        EObject current = null;

        EObject this_ActionFacet_0 = null;

        EObject this_DefinitionFacet_1 = null;

        EObject this_ClassicFacet_2 = null;



        	enterRule();

        try {
            // InternalGaml.g:3661:2: ( (this_ActionFacet_0= ruleActionFacet | this_DefinitionFacet_1= ruleDefinitionFacet | this_ClassicFacet_2= ruleClassicFacet ) )
            // InternalGaml.g:3662:2: (this_ActionFacet_0= ruleActionFacet | this_DefinitionFacet_1= ruleDefinitionFacet | this_ClassicFacet_2= ruleClassicFacet )
            {
            // InternalGaml.g:3662:2: (this_ActionFacet_0= ruleActionFacet | this_DefinitionFacet_1= ruleDefinitionFacet | this_ClassicFacet_2= ruleClassicFacet )
            int alt66=3;
            switch ( input.LA(1) ) {
            case 100:
            case 101:
                {
                alt66=1;
                }
                break;
            case 90:
                {
                alt66=2;
                }
                break;
            case RULE_ID:
            case 15:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 102:
                {
                alt66=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // InternalGaml.g:3663:3: this_ActionFacet_0= ruleActionFacet
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getActionFacetParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ActionFacet_0=ruleActionFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ActionFacet_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3672:3: this_DefinitionFacet_1= ruleDefinitionFacet
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getDefinitionFacetParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_DefinitionFacet_1=ruleDefinitionFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_DefinitionFacet_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3681:3: this_ClassicFacet_2= ruleClassicFacet
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getFacetAccess().getClassicFacetParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ClassicFacet_2=ruleClassicFacet();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ClassicFacet_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFacet"


    // $ANTLR start "entryRuleClassicFacetKey"
    // InternalGaml.g:3693:1: entryRuleClassicFacetKey returns [String current=null] : iv_ruleClassicFacetKey= ruleClassicFacetKey EOF ;
    public final String entryRuleClassicFacetKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleClassicFacetKey = null;


        try {
            // InternalGaml.g:3693:55: (iv_ruleClassicFacetKey= ruleClassicFacetKey EOF )
            // InternalGaml.g:3694:2: iv_ruleClassicFacetKey= ruleClassicFacetKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassicFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassicFacetKey=ruleClassicFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassicFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleClassicFacetKey"


    // $ANTLR start "ruleClassicFacetKey"
    // InternalGaml.g:3700:1: ruleClassicFacetKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID kw= ':' ) ;
    public final AntlrDatatypeRuleToken ruleClassicFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3706:2: ( (this_ID_0= RULE_ID kw= ':' ) )
            // InternalGaml.g:3707:2: (this_ID_0= RULE_ID kw= ':' )
            {
            // InternalGaml.g:3707:2: (this_ID_0= RULE_ID kw= ':' )
            // InternalGaml.g:3708:3: this_ID_0= RULE_ID kw= ':'
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_34); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(this_ID_0);
              		
            }
            if ( state.backtracking==0 ) {

              			newLeafNode(this_ID_0, grammarAccess.getClassicFacetKeyAccess().getIDTerminalRuleCall_0());
              		
            }
            kw=(Token)match(input,89,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current.merge(kw);
              			newLeafNode(kw, grammarAccess.getClassicFacetKeyAccess().getColonKeyword_1());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleClassicFacetKey"


    // $ANTLR start "entryRuleDefinitionFacetKey"
    // InternalGaml.g:3724:1: entryRuleDefinitionFacetKey returns [String current=null] : iv_ruleDefinitionFacetKey= ruleDefinitionFacetKey EOF ;
    public final String entryRuleDefinitionFacetKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleDefinitionFacetKey = null;


        try {
            // InternalGaml.g:3724:58: (iv_ruleDefinitionFacetKey= ruleDefinitionFacetKey EOF )
            // InternalGaml.g:3725:2: iv_ruleDefinitionFacetKey= ruleDefinitionFacetKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinitionFacetKey=ruleDefinitionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinitionFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDefinitionFacetKey"


    // $ANTLR start "ruleDefinitionFacetKey"
    // InternalGaml.g:3731:1: ruleDefinitionFacetKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : kw= 'returns:' ;
    public final AntlrDatatypeRuleToken ruleDefinitionFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3737:2: (kw= 'returns:' )
            // InternalGaml.g:3738:2: kw= 'returns:'
            {
            kw=(Token)match(input,90,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              		current.merge(kw);
              		newLeafNode(kw, grammarAccess.getDefinitionFacetKeyAccess().getReturnsKeyword());
              	
            }

            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefinitionFacetKey"


    // $ANTLR start "entryRuleSpecialFacetKey"
    // InternalGaml.g:3746:1: entryRuleSpecialFacetKey returns [String current=null] : iv_ruleSpecialFacetKey= ruleSpecialFacetKey EOF ;
    public final String entryRuleSpecialFacetKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSpecialFacetKey = null;


        try {
            // InternalGaml.g:3746:55: (iv_ruleSpecialFacetKey= ruleSpecialFacetKey EOF )
            // InternalGaml.g:3747:2: iv_ruleSpecialFacetKey= ruleSpecialFacetKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSpecialFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSpecialFacetKey=ruleSpecialFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSpecialFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSpecialFacetKey"


    // $ANTLR start "ruleSpecialFacetKey"
    // InternalGaml.g:3753:1: ruleSpecialFacetKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'data:' | kw= 'init:' | kw= 'layout:' | kw= 'image:' | kw= 'parameter:' | kw= 'aspect:' | kw= 'light:' | kw= 'as:' | kw= 'species:' ) ;
    public final AntlrDatatypeRuleToken ruleSpecialFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3759:2: ( (kw= 'data:' | kw= 'init:' | kw= 'layout:' | kw= 'image:' | kw= 'parameter:' | kw= 'aspect:' | kw= 'light:' | kw= 'as:' | kw= 'species:' ) )
            // InternalGaml.g:3760:2: (kw= 'data:' | kw= 'init:' | kw= 'layout:' | kw= 'image:' | kw= 'parameter:' | kw= 'aspect:' | kw= 'light:' | kw= 'as:' | kw= 'species:' )
            {
            // InternalGaml.g:3760:2: (kw= 'data:' | kw= 'init:' | kw= 'layout:' | kw= 'image:' | kw= 'parameter:' | kw= 'aspect:' | kw= 'light:' | kw= 'as:' | kw= 'species:' )
            int alt67=9;
            switch ( input.LA(1) ) {
            case 91:
                {
                alt67=1;
                }
                break;
            case 92:
                {
                alt67=2;
                }
                break;
            case 93:
                {
                alt67=3;
                }
                break;
            case 94:
                {
                alt67=4;
                }
                break;
            case 95:
                {
                alt67=5;
                }
                break;
            case 96:
                {
                alt67=6;
                }
                break;
            case 97:
                {
                alt67=7;
                }
                break;
            case 98:
                {
                alt67=8;
                }
                break;
            case 99:
                {
                alt67=9;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // InternalGaml.g:3761:3: kw= 'data:'
                    {
                    kw=(Token)match(input,91,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getDataKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3767:3: kw= 'init:'
                    {
                    kw=(Token)match(input,92,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getInitKeyword_1());
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:3773:3: kw= 'layout:'
                    {
                    kw=(Token)match(input,93,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getLayoutKeyword_2());
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:3779:3: kw= 'image:'
                    {
                    kw=(Token)match(input,94,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getImageKeyword_3());
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:3785:3: kw= 'parameter:'
                    {
                    kw=(Token)match(input,95,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getParameterKeyword_4());
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGaml.g:3791:3: kw= 'aspect:'
                    {
                    kw=(Token)match(input,96,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getAspectKeyword_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGaml.g:3797:3: kw= 'light:'
                    {
                    kw=(Token)match(input,97,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getLightKeyword_6());
                      		
                    }

                    }
                    break;
                case 8 :
                    // InternalGaml.g:3803:3: kw= 'as:'
                    {
                    kw=(Token)match(input,98,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getAsKeyword_7());
                      		
                    }

                    }
                    break;
                case 9 :
                    // InternalGaml.g:3809:3: kw= 'species:'
                    {
                    kw=(Token)match(input,99,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getSpecialFacetKeyAccess().getSpeciesKeyword_8());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSpecialFacetKey"


    // $ANTLR start "entryRuleActionFacetKey"
    // InternalGaml.g:3818:1: entryRuleActionFacetKey returns [String current=null] : iv_ruleActionFacetKey= ruleActionFacetKey EOF ;
    public final String entryRuleActionFacetKey() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleActionFacetKey = null;


        try {
            // InternalGaml.g:3818:54: (iv_ruleActionFacetKey= ruleActionFacetKey EOF )
            // InternalGaml.g:3819:2: iv_ruleActionFacetKey= ruleActionFacetKey EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionFacetKeyRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionFacetKey=ruleActionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionFacetKey.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionFacetKey"


    // $ANTLR start "ruleActionFacetKey"
    // InternalGaml.g:3825:1: ruleActionFacetKey returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'action:' | kw= 'on_change:' ) ;
    public final AntlrDatatypeRuleToken ruleActionFacetKey() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalGaml.g:3831:2: ( (kw= 'action:' | kw= 'on_change:' ) )
            // InternalGaml.g:3832:2: (kw= 'action:' | kw= 'on_change:' )
            {
            // InternalGaml.g:3832:2: (kw= 'action:' | kw= 'on_change:' )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==100) ) {
                alt68=1;
            }
            else if ( (LA68_0==101) ) {
                alt68=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // InternalGaml.g:3833:3: kw= 'action:'
                    {
                    kw=(Token)match(input,100,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getActionFacetKeyAccess().getActionKeyword_0());
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:3839:3: kw= 'on_change:'
                    {
                    kw=(Token)match(input,101,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(kw);
                      			newLeafNode(kw, grammarAccess.getActionFacetKeyAccess().getOn_changeKeyword_1());
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionFacetKey"


    // $ANTLR start "entryRuleClassicFacet"
    // InternalGaml.g:3848:1: entryRuleClassicFacet returns [EObject current=null] : iv_ruleClassicFacet= ruleClassicFacet EOF ;
    public final EObject entryRuleClassicFacet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleClassicFacet = null;


        try {
            // InternalGaml.g:3848:53: (iv_ruleClassicFacet= ruleClassicFacet EOF )
            // InternalGaml.g:3849:2: iv_ruleClassicFacet= ruleClassicFacet EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getClassicFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleClassicFacet=ruleClassicFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleClassicFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleClassicFacet"


    // $ANTLR start "ruleClassicFacet"
    // InternalGaml.g:3855:1: ruleClassicFacet returns [EObject current=null] : ( ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) ) ( (lv_expr_4_0= ruleExpression ) ) ) ;
    public final EObject ruleClassicFacet() throws RecognitionException {
        EObject current = null;

        Token lv_key_1_0=null;
        Token lv_key_2_0=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_key_3_0 = null;

        EObject lv_expr_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:3861:2: ( ( ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) ) ( (lv_expr_4_0= ruleExpression ) ) ) )
            // InternalGaml.g:3862:2: ( ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) ) ( (lv_expr_4_0= ruleExpression ) ) )
            {
            // InternalGaml.g:3862:2: ( ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) ) ( (lv_expr_4_0= ruleExpression ) ) )
            // InternalGaml.g:3863:3: ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) ) ( (lv_expr_4_0= ruleExpression ) )
            {
            // InternalGaml.g:3863:3: ( ( (lv_key_0_0= ruleClassicFacetKey ) ) | ( (lv_key_1_0= '<-' ) ) | ( (lv_key_2_0= '->' ) ) | ( (lv_key_3_0= ruleSpecialFacetKey ) ) )
            int alt69=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt69=1;
                }
                break;
            case 15:
                {
                alt69=2;
                }
                break;
            case 102:
                {
                alt69=3;
                }
                break;
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
                {
                alt69=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // InternalGaml.g:3864:4: ( (lv_key_0_0= ruleClassicFacetKey ) )
                    {
                    // InternalGaml.g:3864:4: ( (lv_key_0_0= ruleClassicFacetKey ) )
                    // InternalGaml.g:3865:5: (lv_key_0_0= ruleClassicFacetKey )
                    {
                    // InternalGaml.g:3865:5: (lv_key_0_0= ruleClassicFacetKey )
                    // InternalGaml.g:3866:6: lv_key_0_0= ruleClassicFacetKey
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassicFacetAccess().getKeyClassicFacetKeyParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_key_0_0=ruleClassicFacetKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassicFacetRule());
                      						}
                      						set(
                      							current,
                      							"key",
                      							lv_key_0_0,
                      							"gama.core.lang.Gaml.ClassicFacetKey");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:3884:4: ( (lv_key_1_0= '<-' ) )
                    {
                    // InternalGaml.g:3884:4: ( (lv_key_1_0= '<-' ) )
                    // InternalGaml.g:3885:5: (lv_key_1_0= '<-' )
                    {
                    // InternalGaml.g:3885:5: (lv_key_1_0= '<-' )
                    // InternalGaml.g:3886:6: lv_key_1_0= '<-'
                    {
                    lv_key_1_0=(Token)match(input,15,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_key_1_0, grammarAccess.getClassicFacetAccess().getKeyLessThanSignHyphenMinusKeyword_0_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassicFacetRule());
                      						}
                      						setWithLastConsumed(current, "key", lv_key_1_0, "<-");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalGaml.g:3899:4: ( (lv_key_2_0= '->' ) )
                    {
                    // InternalGaml.g:3899:4: ( (lv_key_2_0= '->' ) )
                    // InternalGaml.g:3900:5: (lv_key_2_0= '->' )
                    {
                    // InternalGaml.g:3900:5: (lv_key_2_0= '->' )
                    // InternalGaml.g:3901:6: lv_key_2_0= '->'
                    {
                    lv_key_2_0=(Token)match(input,102,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_key_2_0, grammarAccess.getClassicFacetAccess().getKeyHyphenMinusGreaterThanSignKeyword_0_2_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getClassicFacetRule());
                      						}
                      						setWithLastConsumed(current, "key", lv_key_2_0, "->");
                      					
                    }

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalGaml.g:3914:4: ( (lv_key_3_0= ruleSpecialFacetKey ) )
                    {
                    // InternalGaml.g:3914:4: ( (lv_key_3_0= ruleSpecialFacetKey ) )
                    // InternalGaml.g:3915:5: (lv_key_3_0= ruleSpecialFacetKey )
                    {
                    // InternalGaml.g:3915:5: (lv_key_3_0= ruleSpecialFacetKey )
                    // InternalGaml.g:3916:6: lv_key_3_0= ruleSpecialFacetKey
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getClassicFacetAccess().getKeySpecialFacetKeyParserRuleCall_0_3_0());
                      					
                    }
                    pushFollow(FOLLOW_5);
                    lv_key_3_0=ruleSpecialFacetKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getClassicFacetRule());
                      						}
                      						set(
                      							current,
                      							"key",
                      							lv_key_3_0,
                      							"gama.core.lang.Gaml.SpecialFacetKey");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalGaml.g:3934:3: ( (lv_expr_4_0= ruleExpression ) )
            // InternalGaml.g:3935:4: (lv_expr_4_0= ruleExpression )
            {
            // InternalGaml.g:3935:4: (lv_expr_4_0= ruleExpression )
            // InternalGaml.g:3936:5: lv_expr_4_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getClassicFacetAccess().getExprExpressionParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_expr_4_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getClassicFacetRule());
              					}
              					set(
              						current,
              						"expr",
              						lv_expr_4_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleClassicFacet"


    // $ANTLR start "entryRuleDefinitionFacet"
    // InternalGaml.g:3957:1: entryRuleDefinitionFacet returns [EObject current=null] : iv_ruleDefinitionFacet= ruleDefinitionFacet EOF ;
    public final EObject entryRuleDefinitionFacet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDefinitionFacet = null;


        try {
            // InternalGaml.g:3957:56: (iv_ruleDefinitionFacet= ruleDefinitionFacet EOF )
            // InternalGaml.g:3958:2: iv_ruleDefinitionFacet= ruleDefinitionFacet EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getDefinitionFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleDefinitionFacet=ruleDefinitionFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleDefinitionFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDefinitionFacet"


    // $ANTLR start "ruleDefinitionFacet"
    // InternalGaml.g:3964:1: ruleDefinitionFacet returns [EObject current=null] : ( ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ) ;
    public final EObject ruleDefinitionFacet() throws RecognitionException {
        EObject current = null;

        Token lv_name_1_2=null;
        AntlrDatatypeRuleToken lv_key_0_0 = null;

        AntlrDatatypeRuleToken lv_name_1_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:3970:2: ( ( ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) ) )
            // InternalGaml.g:3971:2: ( ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) )
            {
            // InternalGaml.g:3971:2: ( ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) ) )
            // InternalGaml.g:3972:3: ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) ) ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) )
            {
            // InternalGaml.g:3972:3: ( ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey ) )
            // InternalGaml.g:3973:4: ( 'returns:' )=> (lv_key_0_0= ruleDefinitionFacetKey )
            {
            // InternalGaml.g:3974:4: (lv_key_0_0= ruleDefinitionFacetKey )
            // InternalGaml.g:3975:5: lv_key_0_0= ruleDefinitionFacetKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getDefinitionFacetAccess().getKeyDefinitionFacetKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_13);
            lv_key_0_0=ruleDefinitionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getDefinitionFacetRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.DefinitionFacetKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:3992:3: ( ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) ) )
            // InternalGaml.g:3993:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            {
            // InternalGaml.g:3993:4: ( (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING ) )
            // InternalGaml.g:3994:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            {
            // InternalGaml.g:3994:5: (lv_name_1_1= ruleValid_ID | lv_name_1_2= RULE_STRING )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==RULE_ID||LA70_0==36||(LA70_0>=38 && LA70_0<=81)) ) {
                alt70=1;
            }
            else if ( (LA70_0==RULE_STRING) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // InternalGaml.g:3995:6: lv_name_1_1= ruleValid_ID
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getDefinitionFacetAccess().getNameValid_IDParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_name_1_1=ruleValid_ID();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getDefinitionFacetRule());
                      						}
                      						set(
                      							current,
                      							"name",
                      							lv_name_1_1,
                      							"gama.core.lang.Gaml.Valid_ID");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:4011:6: lv_name_1_2= RULE_STRING
                    {
                    lv_name_1_2=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_name_1_2, grammarAccess.getDefinitionFacetAccess().getNameSTRINGTerminalRuleCall_1_0_1());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getDefinitionFacetRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"name",
                      							lv_name_1_2,
                      							"gama.core.lang.Gaml.STRING");
                      					
                    }

                    }
                    break;

            }


            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDefinitionFacet"


    // $ANTLR start "entryRuleActionFacet"
    // InternalGaml.g:4032:1: entryRuleActionFacet returns [EObject current=null] : iv_ruleActionFacet= ruleActionFacet EOF ;
    public final EObject entryRuleActionFacet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionFacet = null;


        try {
            // InternalGaml.g:4032:52: (iv_ruleActionFacet= ruleActionFacet EOF )
            // InternalGaml.g:4033:2: iv_ruleActionFacet= ruleActionFacet EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionFacetRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionFacet=ruleActionFacet();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionFacet; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionFacet"


    // $ANTLR start "ruleActionFacet"
    // InternalGaml.g:4039:1: ruleActionFacet returns [EObject current=null] : ( ( (lv_key_0_0= ruleActionFacetKey ) ) ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) ) ) ;
    public final EObject ruleActionFacet() throws RecognitionException {
        EObject current = null;

        AntlrDatatypeRuleToken lv_key_0_0 = null;

        EObject lv_expr_1_0 = null;

        EObject lv_block_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4045:2: ( ( ( (lv_key_0_0= ruleActionFacetKey ) ) ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) ) ) )
            // InternalGaml.g:4046:2: ( ( (lv_key_0_0= ruleActionFacetKey ) ) ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) ) )
            {
            // InternalGaml.g:4046:2: ( ( (lv_key_0_0= ruleActionFacetKey ) ) ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) ) )
            // InternalGaml.g:4047:3: ( (lv_key_0_0= ruleActionFacetKey ) ) ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) )
            {
            // InternalGaml.g:4047:3: ( (lv_key_0_0= ruleActionFacetKey ) )
            // InternalGaml.g:4048:4: (lv_key_0_0= ruleActionFacetKey )
            {
            // InternalGaml.g:4048:4: (lv_key_0_0= ruleActionFacetKey )
            // InternalGaml.g:4049:5: lv_key_0_0= ruleActionFacetKey
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionFacetAccess().getKeyActionFacetKeyParserRuleCall_0_0());
              				
            }
            pushFollow(FOLLOW_35);
            lv_key_0_0=ruleActionFacetKey();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getActionFacetRule());
              					}
              					set(
              						current,
              						"key",
              						lv_key_0_0,
              						"gama.core.lang.Gaml.ActionFacetKey");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:4066:3: ( ( (lv_expr_1_0= ruleActionRef ) ) | ( (lv_block_2_0= ruleBlock ) ) )
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==RULE_ID||LA71_0==36||(LA71_0>=38 && LA71_0<=81)) ) {
                alt71=1;
            }
            else if ( (LA71_0==32) ) {
                alt71=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }
            switch (alt71) {
                case 1 :
                    // InternalGaml.g:4067:4: ( (lv_expr_1_0= ruleActionRef ) )
                    {
                    // InternalGaml.g:4067:4: ( (lv_expr_1_0= ruleActionRef ) )
                    // InternalGaml.g:4068:5: (lv_expr_1_0= ruleActionRef )
                    {
                    // InternalGaml.g:4068:5: (lv_expr_1_0= ruleActionRef )
                    // InternalGaml.g:4069:6: lv_expr_1_0= ruleActionRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionFacetAccess().getExprActionRefParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_expr_1_0=ruleActionRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionFacetRule());
                      						}
                      						set(
                      							current,
                      							"expr",
                      							lv_expr_1_0,
                      							"gama.core.lang.Gaml.ActionRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:4087:4: ( (lv_block_2_0= ruleBlock ) )
                    {
                    // InternalGaml.g:4087:4: ( (lv_block_2_0= ruleBlock ) )
                    // InternalGaml.g:4088:5: (lv_block_2_0= ruleBlock )
                    {
                    // InternalGaml.g:4088:5: (lv_block_2_0= ruleBlock )
                    // InternalGaml.g:4089:6: lv_block_2_0= ruleBlock
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getActionFacetAccess().getBlockBlockParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_block_2_0=ruleBlock();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getActionFacetRule());
                      						}
                      						set(
                      							current,
                      							"block",
                      							lv_block_2_0,
                      							"gama.core.lang.Gaml.Block");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionFacet"


    // $ANTLR start "entryRuleBlock"
    // InternalGaml.g:4111:1: entryRuleBlock returns [EObject current=null] : iv_ruleBlock= ruleBlock EOF ;
    public final EObject entryRuleBlock() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBlock = null;


        try {
            // InternalGaml.g:4111:46: (iv_ruleBlock= ruleBlock EOF )
            // InternalGaml.g:4112:2: iv_ruleBlock= ruleBlock EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBlockRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBlock=ruleBlock();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBlock; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBlock"


    // $ANTLR start "ruleBlock"
    // InternalGaml.g:4118:1: ruleBlock returns [EObject current=null] : ( () otherlv_1= '{' ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' ) ) ;
    public final EObject ruleBlock() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        EObject lv_statements_2_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4124:2: ( ( () otherlv_1= '{' ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' ) ) )
            // InternalGaml.g:4125:2: ( () otherlv_1= '{' ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' ) )
            {
            // InternalGaml.g:4125:2: ( () otherlv_1= '{' ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' ) )
            // InternalGaml.g:4126:3: () otherlv_1= '{' ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' )
            {
            // InternalGaml.g:4126:3: ()
            // InternalGaml.g:4127:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getBlockAccess().getBlockAction_0(),
              					current);
              			
            }

            }

            otherlv_1=(Token)match(input,32,FOLLOW_36); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_1, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_1());
              		
            }
            // InternalGaml.g:4137:3: ( ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}' )
            // InternalGaml.g:4138:4: ( (lv_statements_2_0= ruleStatement ) )* otherlv_3= '}'
            {
            // InternalGaml.g:4138:4: ( (lv_statements_2_0= ruleStatement ) )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=RULE_ID && LA72_0<=RULE_KEYWORD)||(LA72_0>=23 && LA72_0<=24)||LA72_0==26||(LA72_0>=28 && LA72_0<=29)||(LA72_0>=31 && LA72_0<=32)||(LA72_0>=35 && LA72_0<=81)||(LA72_0>=90 && LA72_0<=101)||LA72_0==112||(LA72_0>=116 && LA72_0<=119)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // InternalGaml.g:4139:5: (lv_statements_2_0= ruleStatement )
            	    {
            	    // InternalGaml.g:4139:5: (lv_statements_2_0= ruleStatement )
            	    // InternalGaml.g:4140:6: lv_statements_2_0= ruleStatement
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getBlockAccess().getStatementsStatementParserRuleCall_2_0_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_36);
            	    lv_statements_2_0=ruleStatement();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getBlockRule());
            	      						}
            	      						add(
            	      							current,
            	      							"statements",
            	      							lv_statements_2_0,
            	      							"gama.core.lang.Gaml.Statement");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            otherlv_3=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_3, grammarAccess.getBlockAccess().getRightCurlyBracketKeyword_2_1());
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBlock"


    // $ANTLR start "entryRuleExpression"
    // InternalGaml.g:4166:1: entryRuleExpression returns [EObject current=null] : iv_ruleExpression= ruleExpression EOF ;
    public final EObject entryRuleExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpression = null;


        try {
            // InternalGaml.g:4166:51: (iv_ruleExpression= ruleExpression EOF )
            // InternalGaml.g:4167:2: iv_ruleExpression= ruleExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpression=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalGaml.g:4173:1: ruleExpression returns [EObject current=null] : ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair ) | this_Pair_1= rulePair ) ;
    public final EObject ruleExpression() throws RecognitionException {
        EObject current = null;

        EObject this_ArgumentPair_0 = null;

        EObject this_Pair_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:4179:2: ( ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair ) | this_Pair_1= rulePair ) )
            // InternalGaml.g:4180:2: ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair ) | this_Pair_1= rulePair )
            {
            // InternalGaml.g:4180:2: ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair ) | this_Pair_1= rulePair )
            int alt73=2;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // InternalGaml.g:4181:3: ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair )
                    {
                    // InternalGaml.g:4181:3: ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair )
                    // InternalGaml.g:4182:4: ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getExpressionAccess().getArgumentPairParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_ArgumentPair_0=ruleArgumentPair();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ArgumentPair_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:4216:3: this_Pair_1= rulePair
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getExpressionAccess().getPairParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Pair_1=rulePair();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Pair_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleArgumentPair"
    // InternalGaml.g:4228:1: entryRuleArgumentPair returns [EObject current=null] : iv_ruleArgumentPair= ruleArgumentPair EOF ;
    public final EObject entryRuleArgumentPair() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArgumentPair = null;


        try {
            // InternalGaml.g:4228:53: (iv_ruleArgumentPair= ruleArgumentPair EOF )
            // InternalGaml.g:4229:2: iv_ruleArgumentPair= ruleArgumentPair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getArgumentPairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleArgumentPair=ruleArgumentPair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleArgumentPair; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArgumentPair"


    // $ANTLR start "ruleArgumentPair"
    // InternalGaml.g:4235:1: ruleArgumentPair returns [EObject current=null] : ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )? ( (lv_right_4_0= rulePair ) ) ) ;
    public final EObject ruleArgumentPair() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_op_0_0 = null;

        AntlrDatatypeRuleToken lv_op_2_1 = null;

        AntlrDatatypeRuleToken lv_op_2_2 = null;

        AntlrDatatypeRuleToken lv_op_2_3 = null;

        EObject lv_right_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4241:2: ( ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )? ( (lv_right_4_0= rulePair ) ) ) )
            // InternalGaml.g:4242:2: ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )? ( (lv_right_4_0= rulePair ) ) )
            {
            // InternalGaml.g:4242:2: ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )? ( (lv_right_4_0= rulePair ) ) )
            // InternalGaml.g:4243:3: ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )? ( (lv_right_4_0= rulePair ) )
            {
            // InternalGaml.g:4243:3: ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )?
            int alt76=2;
            alt76 = dfa76.predict(input);
            switch (alt76) {
                case 1 :
                    // InternalGaml.g:4244:4: ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) )
                    {
                    // InternalGaml.g:4268:4: ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) )
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==RULE_ID||LA75_0==36||(LA75_0>=38 && LA75_0<=81)) ) {
                        alt75=1;
                    }
                    else if ( ((LA75_0>=90 && LA75_0<=101)) ) {
                        alt75=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 75, 0, input);

                        throw nvae;
                    }
                    switch (alt75) {
                        case 1 :
                            // InternalGaml.g:4269:5: ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' )
                            {
                            // InternalGaml.g:4269:5: ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' )
                            // InternalGaml.g:4270:6: ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::'
                            {
                            // InternalGaml.g:4270:6: ( (lv_op_0_0= ruleValid_ID ) )
                            // InternalGaml.g:4271:7: (lv_op_0_0= ruleValid_ID )
                            {
                            // InternalGaml.g:4271:7: (lv_op_0_0= ruleValid_ID )
                            // InternalGaml.g:4272:8: lv_op_0_0= ruleValid_ID
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getArgumentPairAccess().getOpValid_IDParserRuleCall_0_0_0_0_0());
                              							
                            }
                            pushFollow(FOLLOW_37);
                            lv_op_0_0=ruleValid_ID();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getArgumentPairRule());
                              								}
                              								set(
                              									current,
                              									"op",
                              									lv_op_0_0,
                              									"gama.core.lang.Gaml.Valid_ID");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_1=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_1, grammarAccess.getArgumentPairAccess().getColonColonKeyword_0_0_0_1());
                              					
                            }

                            }


                            }
                            break;
                        case 2 :
                            // InternalGaml.g:4295:5: ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' )
                            {
                            // InternalGaml.g:4295:5: ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' )
                            // InternalGaml.g:4296:6: ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':'
                            {
                            // InternalGaml.g:4296:6: ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) )
                            // InternalGaml.g:4297:7: ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) )
                            {
                            // InternalGaml.g:4297:7: ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) )
                            // InternalGaml.g:4298:8: (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey )
                            {
                            // InternalGaml.g:4298:8: (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey )
                            int alt74=3;
                            switch ( input.LA(1) ) {
                            case 90:
                                {
                                alt74=1;
                                }
                                break;
                            case 91:
                            case 92:
                            case 93:
                            case 94:
                            case 95:
                            case 96:
                            case 97:
                            case 98:
                            case 99:
                                {
                                alt74=2;
                                }
                                break;
                            case 100:
                            case 101:
                                {
                                alt74=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 74, 0, input);

                                throw nvae;
                            }

                            switch (alt74) {
                                case 1 :
                                    // InternalGaml.g:4299:9: lv_op_2_1= ruleDefinitionFacetKey
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getArgumentPairAccess().getOpDefinitionFacetKeyParserRuleCall_0_0_1_0_0_0());
                                      								
                                    }
                                    pushFollow(FOLLOW_34);
                                    lv_op_2_1=ruleDefinitionFacetKey();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getArgumentPairRule());
                                      									}
                                      									set(
                                      										current,
                                      										"op",
                                      										lv_op_2_1,
                                      										"gama.core.lang.Gaml.DefinitionFacetKey");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalGaml.g:4315:9: lv_op_2_2= ruleSpecialFacetKey
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getArgumentPairAccess().getOpSpecialFacetKeyParserRuleCall_0_0_1_0_0_1());
                                      								
                                    }
                                    pushFollow(FOLLOW_34);
                                    lv_op_2_2=ruleSpecialFacetKey();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getArgumentPairRule());
                                      									}
                                      									set(
                                      										current,
                                      										"op",
                                      										lv_op_2_2,
                                      										"gama.core.lang.Gaml.SpecialFacetKey");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalGaml.g:4331:9: lv_op_2_3= ruleActionFacetKey
                                    {
                                    if ( state.backtracking==0 ) {

                                      									newCompositeNode(grammarAccess.getArgumentPairAccess().getOpActionFacetKeyParserRuleCall_0_0_1_0_0_2());
                                      								
                                    }
                                    pushFollow(FOLLOW_34);
                                    lv_op_2_3=ruleActionFacetKey();

                                    state._fsp--;
                                    if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElementForParent(grammarAccess.getArgumentPairRule());
                                      									}
                                      									set(
                                      										current,
                                      										"op",
                                      										lv_op_2_3,
                                      										"gama.core.lang.Gaml.ActionFacetKey");
                                      									afterParserOrEnumRuleCall();
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }

                            otherlv_3=(Token)match(input,89,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_3, grammarAccess.getArgumentPairAccess().getColonKeyword_0_0_1_1());
                              					
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            // InternalGaml.g:4356:3: ( (lv_right_4_0= rulePair ) )
            // InternalGaml.g:4357:4: (lv_right_4_0= rulePair )
            {
            // InternalGaml.g:4357:4: (lv_right_4_0= rulePair )
            // InternalGaml.g:4358:5: lv_right_4_0= rulePair
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getArgumentPairAccess().getRightPairParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_right_4_0=rulePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getArgumentPairRule());
              					}
              					set(
              						current,
              						"right",
              						lv_right_4_0,
              						"gama.core.lang.Gaml.Pair");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArgumentPair"


    // $ANTLR start "entryRulePair"
    // InternalGaml.g:4379:1: entryRulePair returns [EObject current=null] : iv_rulePair= rulePair EOF ;
    public final EObject entryRulePair() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePair = null;


        try {
            // InternalGaml.g:4379:45: (iv_rulePair= rulePair EOF )
            // InternalGaml.g:4380:2: iv_rulePair= rulePair EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPairRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePair=rulePair();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePair; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePair"


    // $ANTLR start "rulePair"
    // InternalGaml.g:4386:1: rulePair returns [EObject current=null] : (this_If_0= ruleIf ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )? ) ;
    public final EObject rulePair() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_If_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4392:2: ( (this_If_0= ruleIf ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )? ) )
            // InternalGaml.g:4393:2: (this_If_0= ruleIf ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )? )
            {
            // InternalGaml.g:4393:2: (this_If_0= ruleIf ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )? )
            // InternalGaml.g:4394:3: this_If_0= ruleIf ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getPairAccess().getIfParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_38);
            this_If_0=ruleIf();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_If_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4402:3: ( () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==103) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalGaml.g:4403:4: () ( (lv_op_2_0= '::' ) ) ( (lv_right_3_0= ruleIf ) )
                    {
                    // InternalGaml.g:4403:4: ()
                    // InternalGaml.g:4404:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getPairAccess().getBinaryOperatorLeftAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:4410:4: ( (lv_op_2_0= '::' ) )
                    // InternalGaml.g:4411:5: (lv_op_2_0= '::' )
                    {
                    // InternalGaml.g:4411:5: (lv_op_2_0= '::' )
                    // InternalGaml.g:4412:6: lv_op_2_0= '::'
                    {
                    lv_op_2_0=(Token)match(input,103,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getPairAccess().getOpColonColonKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPairRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_2_0, "::");
                      					
                    }

                    }


                    }

                    // InternalGaml.g:4424:4: ( (lv_right_3_0= ruleIf ) )
                    // InternalGaml.g:4425:5: (lv_right_3_0= ruleIf )
                    {
                    // InternalGaml.g:4425:5: (lv_right_3_0= ruleIf )
                    // InternalGaml.g:4426:6: lv_right_3_0= ruleIf
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPairAccess().getRightIfParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleIf();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPairRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.If");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePair"


    // $ANTLR start "entryRuleIf"
    // InternalGaml.g:4448:1: entryRuleIf returns [EObject current=null] : iv_ruleIf= ruleIf EOF ;
    public final EObject entryRuleIf() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleIf = null;


        try {
            // InternalGaml.g:4448:43: (iv_ruleIf= ruleIf EOF )
            // InternalGaml.g:4449:2: iv_ruleIf= ruleIf EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getIfRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleIf=ruleIf();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleIf; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleIf"


    // $ANTLR start "ruleIf"
    // InternalGaml.g:4455:1: ruleIf returns [EObject current=null] : (this_Or_0= ruleOr ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )? ) ;
    public final EObject ruleIf() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token otherlv_4=null;
        EObject this_Or_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_ifFalse_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4461:2: ( (this_Or_0= ruleOr ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )? ) )
            // InternalGaml.g:4462:2: (this_Or_0= ruleOr ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )? )
            {
            // InternalGaml.g:4462:2: (this_Or_0= ruleOr ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )? )
            // InternalGaml.g:4463:3: this_Or_0= ruleOr ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getIfAccess().getOrParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_39);
            this_Or_0=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Or_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4471:3: ( () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==104) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalGaml.g:4472:4: () ( (lv_op_2_0= '?' ) ) ( (lv_right_3_0= ruleOr ) ) (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) )
                    {
                    // InternalGaml.g:4472:4: ()
                    // InternalGaml.g:4473:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElementAndSet(
                      						grammarAccess.getIfAccess().getIfLeftAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:4479:4: ( (lv_op_2_0= '?' ) )
                    // InternalGaml.g:4480:5: (lv_op_2_0= '?' )
                    {
                    // InternalGaml.g:4480:5: (lv_op_2_0= '?' )
                    // InternalGaml.g:4481:6: lv_op_2_0= '?'
                    {
                    lv_op_2_0=(Token)match(input,104,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getIfAccess().getOpQuestionMarkKeyword_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getIfRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_2_0, "?");
                      					
                    }

                    }


                    }

                    // InternalGaml.g:4493:4: ( (lv_right_3_0= ruleOr ) )
                    // InternalGaml.g:4494:5: (lv_right_3_0= ruleOr )
                    {
                    // InternalGaml.g:4494:5: (lv_right_3_0= ruleOr )
                    // InternalGaml.g:4495:6: lv_right_3_0= ruleOr
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getIfAccess().getRightOrParserRuleCall_1_2_0());
                      					
                    }
                    pushFollow(FOLLOW_34);
                    lv_right_3_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getIfRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.Or");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGaml.g:4512:4: (otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) ) )
                    // InternalGaml.g:4513:5: otherlv_4= ':' ( (lv_ifFalse_5_0= ruleOr ) )
                    {
                    otherlv_4=(Token)match(input,89,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getIfAccess().getColonKeyword_1_3_0());
                      				
                    }
                    // InternalGaml.g:4517:5: ( (lv_ifFalse_5_0= ruleOr ) )
                    // InternalGaml.g:4518:6: (lv_ifFalse_5_0= ruleOr )
                    {
                    // InternalGaml.g:4518:6: (lv_ifFalse_5_0= ruleOr )
                    // InternalGaml.g:4519:7: lv_ifFalse_5_0= ruleOr
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getIfAccess().getIfFalseOrParserRuleCall_1_3_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_ifFalse_5_0=ruleOr();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getIfRule());
                      							}
                      							set(
                      								current,
                      								"ifFalse",
                      								lv_ifFalse_5_0,
                      								"gama.core.lang.Gaml.Or");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleIf"


    // $ANTLR start "entryRuleOr"
    // InternalGaml.g:4542:1: entryRuleOr returns [EObject current=null] : iv_ruleOr= ruleOr EOF ;
    public final EObject entryRuleOr() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOr = null;


        try {
            // InternalGaml.g:4542:43: (iv_ruleOr= ruleOr EOF )
            // InternalGaml.g:4543:2: iv_ruleOr= ruleOr EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getOrRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleOr=ruleOr();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleOr; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOr"


    // $ANTLR start "ruleOr"
    // InternalGaml.g:4549:1: ruleOr returns [EObject current=null] : (this_And_0= ruleAnd ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )* ) ;
    public final EObject ruleOr() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_And_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4555:2: ( (this_And_0= ruleAnd ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )* ) )
            // InternalGaml.g:4556:2: (this_And_0= ruleAnd ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )* )
            {
            // InternalGaml.g:4556:2: (this_And_0= ruleAnd ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )* )
            // InternalGaml.g:4557:3: this_And_0= ruleAnd ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getOrAccess().getAndParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_40);
            this_And_0=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_And_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4565:3: ( () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) ) )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==105) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // InternalGaml.g:4566:4: () ( (lv_op_2_0= 'or' ) ) ( (lv_right_3_0= ruleAnd ) )
            	    {
            	    // InternalGaml.g:4566:4: ()
            	    // InternalGaml.g:4567:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getOrAccess().getBinaryOperatorLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalGaml.g:4573:4: ( (lv_op_2_0= 'or' ) )
            	    // InternalGaml.g:4574:5: (lv_op_2_0= 'or' )
            	    {
            	    // InternalGaml.g:4574:5: (lv_op_2_0= 'or' )
            	    // InternalGaml.g:4575:6: lv_op_2_0= 'or'
            	    {
            	    lv_op_2_0=(Token)match(input,105,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_op_2_0, grammarAccess.getOrAccess().getOpOrKeyword_1_1_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getOrRule());
            	      						}
            	      						setWithLastConsumed(current, "op", lv_op_2_0, "or");
            	      					
            	    }

            	    }


            	    }

            	    // InternalGaml.g:4587:4: ( (lv_right_3_0= ruleAnd ) )
            	    // InternalGaml.g:4588:5: (lv_right_3_0= ruleAnd )
            	    {
            	    // InternalGaml.g:4588:5: (lv_right_3_0= ruleAnd )
            	    // InternalGaml.g:4589:6: lv_right_3_0= ruleAnd
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getOrAccess().getRightAndParserRuleCall_1_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_40);
            	    lv_right_3_0=ruleAnd();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getOrRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.And");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOr"


    // $ANTLR start "entryRuleAnd"
    // InternalGaml.g:4611:1: entryRuleAnd returns [EObject current=null] : iv_ruleAnd= ruleAnd EOF ;
    public final EObject entryRuleAnd() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnd = null;


        try {
            // InternalGaml.g:4611:44: (iv_ruleAnd= ruleAnd EOF )
            // InternalGaml.g:4612:2: iv_ruleAnd= ruleAnd EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAndRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAnd=ruleAnd();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAnd; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnd"


    // $ANTLR start "ruleAnd"
    // InternalGaml.g:4618:1: ruleAnd returns [EObject current=null] : (this_Cast_0= ruleCast ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )* ) ;
    public final EObject ruleAnd() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_Cast_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4624:2: ( (this_Cast_0= ruleCast ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )* ) )
            // InternalGaml.g:4625:2: (this_Cast_0= ruleCast ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )* )
            {
            // InternalGaml.g:4625:2: (this_Cast_0= ruleCast ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )* )
            // InternalGaml.g:4626:3: this_Cast_0= ruleCast ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAndAccess().getCastParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_41);
            this_Cast_0=ruleCast();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Cast_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4634:3: ( () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) ) )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==106) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // InternalGaml.g:4635:4: () ( (lv_op_2_0= 'and' ) ) ( (lv_right_3_0= ruleCast ) )
            	    {
            	    // InternalGaml.g:4635:4: ()
            	    // InternalGaml.g:4636:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getAndAccess().getBinaryOperatorLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalGaml.g:4642:4: ( (lv_op_2_0= 'and' ) )
            	    // InternalGaml.g:4643:5: (lv_op_2_0= 'and' )
            	    {
            	    // InternalGaml.g:4643:5: (lv_op_2_0= 'and' )
            	    // InternalGaml.g:4644:6: lv_op_2_0= 'and'
            	    {
            	    lv_op_2_0=(Token)match(input,106,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						newLeafNode(lv_op_2_0, grammarAccess.getAndAccess().getOpAndKeyword_1_1_0());
            	      					
            	    }
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElement(grammarAccess.getAndRule());
            	      						}
            	      						setWithLastConsumed(current, "op", lv_op_2_0, "and");
            	      					
            	    }

            	    }


            	    }

            	    // InternalGaml.g:4656:4: ( (lv_right_3_0= ruleCast ) )
            	    // InternalGaml.g:4657:5: (lv_right_3_0= ruleCast )
            	    {
            	    // InternalGaml.g:4657:5: (lv_right_3_0= ruleCast )
            	    // InternalGaml.g:4658:6: lv_right_3_0= ruleCast
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getAndAccess().getRightCastParserRuleCall_1_2_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_41);
            	    lv_right_3_0=ruleCast();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getAndRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Cast");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnd"


    // $ANTLR start "entryRuleCast"
    // InternalGaml.g:4680:1: entryRuleCast returns [EObject current=null] : iv_ruleCast= ruleCast EOF ;
    public final EObject entryRuleCast() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCast = null;


        try {
            // InternalGaml.g:4680:45: (iv_ruleCast= ruleCast EOF )
            // InternalGaml.g:4681:2: iv_ruleCast= ruleCast EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getCastRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleCast=ruleCast();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleCast; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCast"


    // $ANTLR start "ruleCast"
    // InternalGaml.g:4687:1: ruleCast returns [EObject current=null] : (this_Comparison_0= ruleComparison ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )? ) ;
    public final EObject ruleCast() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        EObject this_Comparison_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4693:2: ( (this_Comparison_0= ruleComparison ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )? ) )
            // InternalGaml.g:4694:2: (this_Comparison_0= ruleComparison ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )? )
            {
            // InternalGaml.g:4694:2: (this_Comparison_0= ruleComparison ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )? )
            // InternalGaml.g:4695:3: this_Comparison_0= ruleComparison ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getCastAccess().getComparisonParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_11);
            this_Comparison_0=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Comparison_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4703:3: ( ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==18) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalGaml.g:4704:4: ( () ( (lv_op_2_0= 'as' ) ) ) ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) )
                    {
                    // InternalGaml.g:4704:4: ( () ( (lv_op_2_0= 'as' ) ) )
                    // InternalGaml.g:4705:5: () ( (lv_op_2_0= 'as' ) )
                    {
                    // InternalGaml.g:4705:5: ()
                    // InternalGaml.g:4706:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getCastAccess().getBinaryOperatorLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    // InternalGaml.g:4712:5: ( (lv_op_2_0= 'as' ) )
                    // InternalGaml.g:4713:6: (lv_op_2_0= 'as' )
                    {
                    // InternalGaml.g:4713:6: (lv_op_2_0= 'as' )
                    // InternalGaml.g:4714:7: lv_op_2_0= 'as'
                    {
                    lv_op_2_0=(Token)match(input,18,FOLLOW_42); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_op_2_0, grammarAccess.getCastAccess().getOpAsKeyword_1_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getCastRule());
                      							}
                      							setWithLastConsumed(current, "op", lv_op_2_0, "as");
                      						
                    }

                    }


                    }


                    }

                    // InternalGaml.g:4727:4: ( ( (lv_right_3_0= ruleTypeRef ) ) | (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' ) )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( (LA81_0==RULE_ID||LA81_0==38) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==29) ) {
                        alt81=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 0, input);

                        throw nvae;
                    }
                    switch (alt81) {
                        case 1 :
                            // InternalGaml.g:4728:5: ( (lv_right_3_0= ruleTypeRef ) )
                            {
                            // InternalGaml.g:4728:5: ( (lv_right_3_0= ruleTypeRef ) )
                            // InternalGaml.g:4729:6: (lv_right_3_0= ruleTypeRef )
                            {
                            // InternalGaml.g:4729:6: (lv_right_3_0= ruleTypeRef )
                            // InternalGaml.g:4730:7: lv_right_3_0= ruleTypeRef
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getCastAccess().getRightTypeRefParserRuleCall_1_1_0_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_3_0=ruleTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getCastRule());
                              							}
                              							set(
                              								current,
                              								"right",
                              								lv_right_3_0,
                              								"gama.core.lang.Gaml.TypeRef");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalGaml.g:4748:5: (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' )
                            {
                            // InternalGaml.g:4748:5: (otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')' )
                            // InternalGaml.g:4749:6: otherlv_4= '(' ( (lv_right_5_0= ruleTypeRef ) ) otherlv_6= ')'
                            {
                            otherlv_4=(Token)match(input,29,FOLLOW_23); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_4, grammarAccess.getCastAccess().getLeftParenthesisKeyword_1_1_1_0());
                              					
                            }
                            // InternalGaml.g:4753:6: ( (lv_right_5_0= ruleTypeRef ) )
                            // InternalGaml.g:4754:7: (lv_right_5_0= ruleTypeRef )
                            {
                            // InternalGaml.g:4754:7: (lv_right_5_0= ruleTypeRef )
                            // InternalGaml.g:4755:8: lv_right_5_0= ruleTypeRef
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getCastAccess().getRightTypeRefParserRuleCall_1_1_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_24);
                            lv_right_5_0=ruleTypeRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getCastRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_5_0,
                              									"gama.core.lang.Gaml.TypeRef");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }

                            otherlv_6=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						newLeafNode(otherlv_6, grammarAccess.getCastAccess().getRightParenthesisKeyword_1_1_1_2());
                              					
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCast"


    // $ANTLR start "entryRuleComparison"
    // InternalGaml.g:4783:1: entryRuleComparison returns [EObject current=null] : iv_ruleComparison= ruleComparison EOF ;
    public final EObject entryRuleComparison() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleComparison = null;


        try {
            // InternalGaml.g:4783:51: (iv_ruleComparison= ruleComparison EOF )
            // InternalGaml.g:4784:2: iv_ruleComparison= ruleComparison EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getComparisonRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleComparison=ruleComparison();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleComparison; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleComparison"


    // $ANTLR start "ruleComparison"
    // InternalGaml.g:4790:1: ruleComparison returns [EObject current=null] : (this_Addition_0= ruleAddition ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )? ) ;
    public final EObject ruleComparison() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        Token lv_op_2_3=null;
        Token lv_op_2_4=null;
        Token lv_op_2_5=null;
        Token lv_op_2_6=null;
        EObject this_Addition_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4796:2: ( (this_Addition_0= ruleAddition ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )? ) )
            // InternalGaml.g:4797:2: (this_Addition_0= ruleAddition ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )? )
            {
            // InternalGaml.g:4797:2: (this_Addition_0= ruleAddition ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )? )
            // InternalGaml.g:4798:3: this_Addition_0= ruleAddition ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getComparisonAccess().getAdditionParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_43);
            this_Addition_0=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Addition_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4806:3: ( ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) ) )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==34||(LA84_0>=107 && LA84_0<=110)) ) {
                alt84=1;
            }
            else if ( (LA84_0==83) ) {
                int LA84_2 = input.LA(2);

                if ( ((LA84_2>=RULE_ID && LA84_2<=RULE_KEYWORD)||LA84_2==29||LA84_2==32||LA84_2==36||(LA84_2>=38 && LA84_2<=81)||LA84_2==112||(LA84_2>=116 && LA84_2<=119)) ) {
                    alt84=1;
                }
            }
            switch (alt84) {
                case 1 :
                    // InternalGaml.g:4807:4: ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) ) ( (lv_right_3_0= ruleAddition ) )
                    {
                    // InternalGaml.g:4807:4: ( () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) ) )
                    // InternalGaml.g:4808:5: () ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) )
                    {
                    // InternalGaml.g:4808:5: ()
                    // InternalGaml.g:4809:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getComparisonAccess().getBinaryOperatorLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    // InternalGaml.g:4815:5: ( ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) ) )
                    // InternalGaml.g:4816:6: ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) )
                    {
                    // InternalGaml.g:4816:6: ( (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' ) )
                    // InternalGaml.g:4817:7: (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' )
                    {
                    // InternalGaml.g:4817:7: (lv_op_2_1= '!=' | lv_op_2_2= '=' | lv_op_2_3= '>=' | lv_op_2_4= '<=' | lv_op_2_5= '<' | lv_op_2_6= '>' )
                    int alt83=6;
                    switch ( input.LA(1) ) {
                    case 107:
                        {
                        alt83=1;
                        }
                        break;
                    case 34:
                        {
                        alt83=2;
                        }
                        break;
                    case 108:
                        {
                        alt83=3;
                        }
                        break;
                    case 109:
                        {
                        alt83=4;
                        }
                        break;
                    case 110:
                        {
                        alt83=5;
                        }
                        break;
                    case 83:
                        {
                        alt83=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 83, 0, input);

                        throw nvae;
                    }

                    switch (alt83) {
                        case 1 :
                            // InternalGaml.g:4818:8: lv_op_2_1= '!='
                            {
                            lv_op_2_1=(Token)match(input,107,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_1, grammarAccess.getComparisonAccess().getOpExclamationMarkEqualsSignKeyword_1_0_1_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_1, null);
                              							
                            }

                            }
                            break;
                        case 2 :
                            // InternalGaml.g:4829:8: lv_op_2_2= '='
                            {
                            lv_op_2_2=(Token)match(input,34,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_2, grammarAccess.getComparisonAccess().getOpEqualsSignKeyword_1_0_1_0_1());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_2, null);
                              							
                            }

                            }
                            break;
                        case 3 :
                            // InternalGaml.g:4840:8: lv_op_2_3= '>='
                            {
                            lv_op_2_3=(Token)match(input,108,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_3, grammarAccess.getComparisonAccess().getOpGreaterThanSignEqualsSignKeyword_1_0_1_0_2());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_3, null);
                              							
                            }

                            }
                            break;
                        case 4 :
                            // InternalGaml.g:4851:8: lv_op_2_4= '<='
                            {
                            lv_op_2_4=(Token)match(input,109,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_4, grammarAccess.getComparisonAccess().getOpLessThanSignEqualsSignKeyword_1_0_1_0_3());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_4, null);
                              							
                            }

                            }
                            break;
                        case 5 :
                            // InternalGaml.g:4862:8: lv_op_2_5= '<'
                            {
                            lv_op_2_5=(Token)match(input,110,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_5, grammarAccess.getComparisonAccess().getOpLessThanSignKeyword_1_0_1_0_4());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_5, null);
                              							
                            }

                            }
                            break;
                        case 6 :
                            // InternalGaml.g:4873:8: lv_op_2_6= '>'
                            {
                            lv_op_2_6=(Token)match(input,83,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_6, grammarAccess.getComparisonAccess().getOpGreaterThanSignKeyword_1_0_1_0_5());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getComparisonRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_6, null);
                              							
                            }

                            }
                            break;

                    }


                    }


                    }


                    }

                    // InternalGaml.g:4887:4: ( (lv_right_3_0= ruleAddition ) )
                    // InternalGaml.g:4888:5: (lv_right_3_0= ruleAddition )
                    {
                    // InternalGaml.g:4888:5: (lv_right_3_0= ruleAddition )
                    // InternalGaml.g:4889:6: lv_right_3_0= ruleAddition
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getComparisonAccess().getRightAdditionParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleAddition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getComparisonRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.Addition");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComparison"


    // $ANTLR start "entryRuleAddition"
    // InternalGaml.g:4911:1: entryRuleAddition returns [EObject current=null] : iv_ruleAddition= ruleAddition EOF ;
    public final EObject entryRuleAddition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAddition = null;


        try {
            // InternalGaml.g:4911:49: (iv_ruleAddition= ruleAddition EOF )
            // InternalGaml.g:4912:2: iv_ruleAddition= ruleAddition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAdditionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAddition=ruleAddition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAddition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAddition"


    // $ANTLR start "ruleAddition"
    // InternalGaml.g:4918:1: ruleAddition returns [EObject current=null] : (this_Multiplication_0= ruleMultiplication ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) ;
    public final EObject ruleAddition() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Multiplication_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:4924:2: ( (this_Multiplication_0= ruleMultiplication ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* ) )
            // InternalGaml.g:4925:2: (this_Multiplication_0= ruleMultiplication ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            {
            // InternalGaml.g:4925:2: (this_Multiplication_0= ruleMultiplication ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )* )
            // InternalGaml.g:4926:3: this_Multiplication_0= ruleMultiplication ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAdditionAccess().getMultiplicationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_44);
            this_Multiplication_0=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Multiplication_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:4934:3: ( ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) ) )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( ((LA86_0>=111 && LA86_0<=112)) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // InternalGaml.g:4935:4: ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) ) ( (lv_right_3_0= ruleMultiplication ) )
            	    {
            	    // InternalGaml.g:4935:4: ( () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) ) )
            	    // InternalGaml.g:4936:5: () ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) )
            	    {
            	    // InternalGaml.g:4936:5: ()
            	    // InternalGaml.g:4937:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getAdditionAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGaml.g:4943:5: ( ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) ) )
            	    // InternalGaml.g:4944:6: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    {
            	    // InternalGaml.g:4944:6: ( (lv_op_2_1= '+' | lv_op_2_2= '-' ) )
            	    // InternalGaml.g:4945:7: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    {
            	    // InternalGaml.g:4945:7: (lv_op_2_1= '+' | lv_op_2_2= '-' )
            	    int alt85=2;
            	    int LA85_0 = input.LA(1);

            	    if ( (LA85_0==111) ) {
            	        alt85=1;
            	    }
            	    else if ( (LA85_0==112) ) {
            	        alt85=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 85, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt85) {
            	        case 1 :
            	            // InternalGaml.g:4946:8: lv_op_2_1= '+'
            	            {
            	            lv_op_2_1=(Token)match(input,111,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getAdditionAccess().getOpPlusSignKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAdditionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalGaml.g:4957:8: lv_op_2_2= '-'
            	            {
            	            lv_op_2_2=(Token)match(input,112,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getAdditionAccess().getOpHyphenMinusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAdditionRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalGaml.g:4971:4: ( (lv_right_3_0= ruleMultiplication ) )
            	    // InternalGaml.g:4972:5: (lv_right_3_0= ruleMultiplication )
            	    {
            	    // InternalGaml.g:4972:5: (lv_right_3_0= ruleMultiplication )
            	    // InternalGaml.g:4973:6: lv_right_3_0= ruleMultiplication
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getAdditionAccess().getRightMultiplicationParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_44);
            	    lv_right_3_0=ruleMultiplication();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getAdditionRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Multiplication");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAddition"


    // $ANTLR start "entryRuleMultiplication"
    // InternalGaml.g:4995:1: entryRuleMultiplication returns [EObject current=null] : iv_ruleMultiplication= ruleMultiplication EOF ;
    public final EObject entryRuleMultiplication() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMultiplication = null;


        try {
            // InternalGaml.g:4995:55: (iv_ruleMultiplication= ruleMultiplication EOF )
            // InternalGaml.g:4996:2: iv_ruleMultiplication= ruleMultiplication EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getMultiplicationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleMultiplication=ruleMultiplication();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleMultiplication; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMultiplication"


    // $ANTLR start "ruleMultiplication"
    // InternalGaml.g:5002:1: ruleMultiplication returns [EObject current=null] : (this_Exponentiation_0= ruleExponentiation ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )* ) ;
    public final EObject ruleMultiplication() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_1=null;
        Token lv_op_2_2=null;
        EObject this_Exponentiation_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5008:2: ( (this_Exponentiation_0= ruleExponentiation ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )* ) )
            // InternalGaml.g:5009:2: (this_Exponentiation_0= ruleExponentiation ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )* )
            {
            // InternalGaml.g:5009:2: (this_Exponentiation_0= ruleExponentiation ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )* )
            // InternalGaml.g:5010:3: this_Exponentiation_0= ruleExponentiation ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getMultiplicationAccess().getExponentiationParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_45);
            this_Exponentiation_0=ruleExponentiation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Exponentiation_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:5018:3: ( ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) ) )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( ((LA88_0>=113 && LA88_0<=114)) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // InternalGaml.g:5019:4: ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) ) ( (lv_right_3_0= ruleExponentiation ) )
            	    {
            	    // InternalGaml.g:5019:4: ( () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) ) )
            	    // InternalGaml.g:5020:5: () ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) )
            	    {
            	    // InternalGaml.g:5020:5: ()
            	    // InternalGaml.g:5021:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getMultiplicationAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGaml.g:5027:5: ( ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) ) )
            	    // InternalGaml.g:5028:6: ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) )
            	    {
            	    // InternalGaml.g:5028:6: ( (lv_op_2_1= '*' | lv_op_2_2= '/' ) )
            	    // InternalGaml.g:5029:7: (lv_op_2_1= '*' | lv_op_2_2= '/' )
            	    {
            	    // InternalGaml.g:5029:7: (lv_op_2_1= '*' | lv_op_2_2= '/' )
            	    int alt87=2;
            	    int LA87_0 = input.LA(1);

            	    if ( (LA87_0==113) ) {
            	        alt87=1;
            	    }
            	    else if ( (LA87_0==114) ) {
            	        alt87=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 87, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt87) {
            	        case 1 :
            	            // InternalGaml.g:5030:8: lv_op_2_1= '*'
            	            {
            	            lv_op_2_1=(Token)match(input,113,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_1, grammarAccess.getMultiplicationAccess().getOpAsteriskKeyword_1_0_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getMultiplicationRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_1, null);
            	              							
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // InternalGaml.g:5041:8: lv_op_2_2= '/'
            	            {
            	            lv_op_2_2=(Token)match(input,114,FOLLOW_5); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_2, grammarAccess.getMultiplicationAccess().getOpSolidusKeyword_1_0_1_0_1());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getMultiplicationRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_2, null);
            	              							
            	            }

            	            }
            	            break;

            	    }


            	    }


            	    }


            	    }

            	    // InternalGaml.g:5055:4: ( (lv_right_3_0= ruleExponentiation ) )
            	    // InternalGaml.g:5056:5: (lv_right_3_0= ruleExponentiation )
            	    {
            	    // InternalGaml.g:5056:5: (lv_right_3_0= ruleExponentiation )
            	    // InternalGaml.g:5057:6: lv_right_3_0= ruleExponentiation
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getMultiplicationAccess().getRightExponentiationParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_45);
            	    lv_right_3_0=ruleExponentiation();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getMultiplicationRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Exponentiation");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMultiplication"


    // $ANTLR start "entryRuleExponentiation"
    // InternalGaml.g:5079:1: entryRuleExponentiation returns [EObject current=null] : iv_ruleExponentiation= ruleExponentiation EOF ;
    public final EObject entryRuleExponentiation() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExponentiation = null;


        try {
            // InternalGaml.g:5079:55: (iv_ruleExponentiation= ruleExponentiation EOF )
            // InternalGaml.g:5080:2: iv_ruleExponentiation= ruleExponentiation EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExponentiationRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExponentiation=ruleExponentiation();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExponentiation; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExponentiation"


    // $ANTLR start "ruleExponentiation"
    // InternalGaml.g:5086:1: ruleExponentiation returns [EObject current=null] : (this_Binary_0= ruleBinary ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )* ) ;
    public final EObject ruleExponentiation() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_Binary_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5092:2: ( (this_Binary_0= ruleBinary ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )* ) )
            // InternalGaml.g:5093:2: (this_Binary_0= ruleBinary ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )* )
            {
            // InternalGaml.g:5093:2: (this_Binary_0= ruleBinary ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )* )
            // InternalGaml.g:5094:3: this_Binary_0= ruleBinary ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getExponentiationAccess().getBinaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_46);
            this_Binary_0=ruleBinary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Binary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:5102:3: ( ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==115) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalGaml.g:5103:4: ( () ( (lv_op_2_0= '^' ) ) ) ( (lv_right_3_0= ruleBinary ) )
            	    {
            	    // InternalGaml.g:5103:4: ( () ( (lv_op_2_0= '^' ) ) )
            	    // InternalGaml.g:5104:5: () ( (lv_op_2_0= '^' ) )
            	    {
            	    // InternalGaml.g:5104:5: ()
            	    // InternalGaml.g:5105:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getExponentiationAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGaml.g:5111:5: ( (lv_op_2_0= '^' ) )
            	    // InternalGaml.g:5112:6: (lv_op_2_0= '^' )
            	    {
            	    // InternalGaml.g:5112:6: (lv_op_2_0= '^' )
            	    // InternalGaml.g:5113:7: lv_op_2_0= '^'
            	    {
            	    lv_op_2_0=(Token)match(input,115,FOLLOW_5); if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							newLeafNode(lv_op_2_0, grammarAccess.getExponentiationAccess().getOpCircumflexAccentKeyword_1_0_1_0());
            	      						
            	    }
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElement(grammarAccess.getExponentiationRule());
            	      							}
            	      							setWithLastConsumed(current, "op", lv_op_2_0, "^");
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalGaml.g:5126:4: ( (lv_right_3_0= ruleBinary ) )
            	    // InternalGaml.g:5127:5: (lv_right_3_0= ruleBinary )
            	    {
            	    // InternalGaml.g:5127:5: (lv_right_3_0= ruleBinary )
            	    // InternalGaml.g:5128:6: lv_right_3_0= ruleBinary
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getExponentiationAccess().getRightBinaryParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_46);
            	    lv_right_3_0=ruleBinary();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getExponentiationRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Binary");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExponentiation"


    // $ANTLR start "entryRuleBinary"
    // InternalGaml.g:5150:1: entryRuleBinary returns [EObject current=null] : iv_ruleBinary= ruleBinary EOF ;
    public final EObject entryRuleBinary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBinary = null;


        try {
            // InternalGaml.g:5150:47: (iv_ruleBinary= ruleBinary EOF )
            // InternalGaml.g:5151:2: iv_ruleBinary= ruleBinary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getBinaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleBinary=ruleBinary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleBinary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBinary"


    // $ANTLR start "ruleBinary"
    // InternalGaml.g:5157:1: ruleBinary returns [EObject current=null] : (this_Unit_0= ruleUnit ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )* ) ;
    public final EObject ruleBinary() throws RecognitionException {
        EObject current = null;

        EObject this_Unit_0 = null;

        AntlrDatatypeRuleToken lv_op_2_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5163:2: ( (this_Unit_0= ruleUnit ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )* ) )
            // InternalGaml.g:5164:2: (this_Unit_0= ruleUnit ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )* )
            {
            // InternalGaml.g:5164:2: (this_Unit_0= ruleUnit ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )* )
            // InternalGaml.g:5165:3: this_Unit_0= ruleUnit ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getBinaryAccess().getUnitParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_47);
            this_Unit_0=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Unit_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:5173:3: ( ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) ) )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==RULE_ID) ) {
                    int LA90_2 = input.LA(2);

                    if ( ((LA90_2>=RULE_ID && LA90_2<=RULE_KEYWORD)||LA90_2==29||LA90_2==32||LA90_2==36||(LA90_2>=38 && LA90_2<=81)||LA90_2==112||(LA90_2>=116 && LA90_2<=119)) ) {
                        alt90=1;
                    }


                }
                else if ( (LA90_0==36||(LA90_0>=38 && LA90_0<=81)) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // InternalGaml.g:5174:4: ( () ( (lv_op_2_0= ruleValid_ID ) ) ) ( (lv_right_3_0= ruleUnit ) )
            	    {
            	    // InternalGaml.g:5174:4: ( () ( (lv_op_2_0= ruleValid_ID ) ) )
            	    // InternalGaml.g:5175:5: () ( (lv_op_2_0= ruleValid_ID ) )
            	    {
            	    // InternalGaml.g:5175:5: ()
            	    // InternalGaml.g:5176:6: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      						current = forceCreateModelElementAndSet(
            	      							grammarAccess.getBinaryAccess().getBinaryOperatorLeftAction_1_0_0(),
            	      							current);
            	      					
            	    }

            	    }

            	    // InternalGaml.g:5182:5: ( (lv_op_2_0= ruleValid_ID ) )
            	    // InternalGaml.g:5183:6: (lv_op_2_0= ruleValid_ID )
            	    {
            	    // InternalGaml.g:5183:6: (lv_op_2_0= ruleValid_ID )
            	    // InternalGaml.g:5184:7: lv_op_2_0= ruleValid_ID
            	    {
            	    if ( state.backtracking==0 ) {

            	      							newCompositeNode(grammarAccess.getBinaryAccess().getOpValid_IDParserRuleCall_1_0_1_0());
            	      						
            	    }
            	    pushFollow(FOLLOW_5);
            	    lv_op_2_0=ruleValid_ID();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      							if (current==null) {
            	      								current = createModelElementForParent(grammarAccess.getBinaryRule());
            	      							}
            	      							set(
            	      								current,
            	      								"op",
            	      								lv_op_2_0,
            	      								"gama.core.lang.Gaml.Valid_ID");
            	      							afterParserOrEnumRuleCall();
            	      						
            	    }

            	    }


            	    }


            	    }

            	    // InternalGaml.g:5202:4: ( (lv_right_3_0= ruleUnit ) )
            	    // InternalGaml.g:5203:5: (lv_right_3_0= ruleUnit )
            	    {
            	    // InternalGaml.g:5203:5: (lv_right_3_0= ruleUnit )
            	    // InternalGaml.g:5204:6: lv_right_3_0= ruleUnit
            	    {
            	    if ( state.backtracking==0 ) {

            	      						newCompositeNode(grammarAccess.getBinaryAccess().getRightUnitParserRuleCall_1_1_0());
            	      					
            	    }
            	    pushFollow(FOLLOW_47);
            	    lv_right_3_0=ruleUnit();

            	    state._fsp--;
            	    if (state.failed) return current;
            	    if ( state.backtracking==0 ) {

            	      						if (current==null) {
            	      							current = createModelElementForParent(grammarAccess.getBinaryRule());
            	      						}
            	      						set(
            	      							current,
            	      							"right",
            	      							lv_right_3_0,
            	      							"gama.core.lang.Gaml.Unit");
            	      						afterParserOrEnumRuleCall();
            	      					
            	    }

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBinary"


    // $ANTLR start "entryRuleUnit"
    // InternalGaml.g:5226:1: entryRuleUnit returns [EObject current=null] : iv_ruleUnit= ruleUnit EOF ;
    public final EObject entryRuleUnit() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnit = null;


        try {
            // InternalGaml.g:5226:45: (iv_ruleUnit= ruleUnit EOF )
            // InternalGaml.g:5227:2: iv_ruleUnit= ruleUnit EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnit=ruleUnit();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnit; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnit"


    // $ANTLR start "ruleUnit"
    // InternalGaml.g:5233:1: ruleUnit returns [EObject current=null] : (this_Unary_0= ruleUnary ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )? ) ;
    public final EObject ruleUnit() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        EObject this_Unary_0 = null;

        EObject lv_right_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5239:2: ( (this_Unary_0= ruleUnary ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )? ) )
            // InternalGaml.g:5240:2: (this_Unary_0= ruleUnary ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )? )
            {
            // InternalGaml.g:5240:2: (this_Unary_0= ruleUnary ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )? )
            // InternalGaml.g:5241:3: this_Unary_0= ruleUnary ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )?
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getUnitAccess().getUnaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_48);
            this_Unary_0=ruleUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Unary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:5249:3: ( ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==116) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalGaml.g:5250:4: ( () ( (lv_op_2_0= '#' ) ) ) ( (lv_right_3_0= ruleUnitRef ) )
                    {
                    // InternalGaml.g:5250:4: ( () ( (lv_op_2_0= '#' ) ) )
                    // InternalGaml.g:5251:5: () ( (lv_op_2_0= '#' ) )
                    {
                    // InternalGaml.g:5251:5: ()
                    // InternalGaml.g:5252:6: 
                    {
                    if ( state.backtracking==0 ) {

                      						current = forceCreateModelElementAndSet(
                      							grammarAccess.getUnitAccess().getUnitLeftAction_1_0_0(),
                      							current);
                      					
                    }

                    }

                    // InternalGaml.g:5258:5: ( (lv_op_2_0= '#' ) )
                    // InternalGaml.g:5259:6: (lv_op_2_0= '#' )
                    {
                    // InternalGaml.g:5259:6: (lv_op_2_0= '#' )
                    // InternalGaml.g:5260:7: lv_op_2_0= '#'
                    {
                    lv_op_2_0=(Token)match(input,116,FOLLOW_7); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(lv_op_2_0, grammarAccess.getUnitAccess().getOpNumberSignKeyword_1_0_1_0());
                      						
                    }
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getUnitRule());
                      							}
                      							setWithLastConsumed(current, "op", lv_op_2_0, "#");
                      						
                    }

                    }


                    }


                    }

                    // InternalGaml.g:5273:4: ( (lv_right_3_0= ruleUnitRef ) )
                    // InternalGaml.g:5274:5: (lv_right_3_0= ruleUnitRef )
                    {
                    // InternalGaml.g:5274:5: (lv_right_3_0= ruleUnitRef )
                    // InternalGaml.g:5275:6: lv_right_3_0= ruleUnitRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getUnitAccess().getRightUnitRefParserRuleCall_1_1_0());
                      					
                    }
                    pushFollow(FOLLOW_2);
                    lv_right_3_0=ruleUnitRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getUnitRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_3_0,
                      							"gama.core.lang.Gaml.UnitRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnit"


    // $ANTLR start "entryRuleUnary"
    // InternalGaml.g:5297:1: entryRuleUnary returns [EObject current=null] : iv_ruleUnary= ruleUnary EOF ;
    public final EObject entryRuleUnary() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnary = null;


        try {
            // InternalGaml.g:5297:46: (iv_ruleUnary= ruleUnary EOF )
            // InternalGaml.g:5298:2: iv_ruleUnary= ruleUnary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnary=ruleUnary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnary"


    // $ANTLR start "ruleUnary"
    // InternalGaml.g:5304:1: ruleUnary returns [EObject current=null] : (this_Access_0= ruleAccess | ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) ) ) ;
    public final EObject ruleUnary() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token lv_op_4_1=null;
        Token lv_op_4_2=null;
        Token lv_op_4_3=null;
        EObject this_Access_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5310:2: ( (this_Access_0= ruleAccess | ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) ) ) )
            // InternalGaml.g:5311:2: (this_Access_0= ruleAccess | ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) ) )
            {
            // InternalGaml.g:5311:2: (this_Access_0= ruleAccess | ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) ) )
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( ((LA94_0>=RULE_ID && LA94_0<=RULE_KEYWORD)||LA94_0==29||LA94_0==32||LA94_0==36||(LA94_0>=38 && LA94_0<=81)||LA94_0==119) ) {
                alt94=1;
            }
            else if ( (LA94_0==112||(LA94_0>=116 && LA94_0<=118)) ) {
                alt94=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }
            switch (alt94) {
                case 1 :
                    // InternalGaml.g:5312:3: this_Access_0= ruleAccess
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getUnaryAccess().getAccessParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_Access_0=ruleAccess();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_Access_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:5321:3: ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) )
                    {
                    // InternalGaml.g:5321:3: ( () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) ) )
                    // InternalGaml.g:5322:4: () ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) )
                    {
                    // InternalGaml.g:5322:4: ()
                    // InternalGaml.g:5323:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getUnaryAccess().getUnaryAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:5329:4: ( ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) ) | ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) ) )
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==116) ) {
                        alt93=1;
                    }
                    else if ( (LA93_0==112||(LA93_0>=117 && LA93_0<=118)) ) {
                        alt93=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 93, 0, input);

                        throw nvae;
                    }
                    switch (alt93) {
                        case 1 :
                            // InternalGaml.g:5330:5: ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) )
                            {
                            // InternalGaml.g:5330:5: ( ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) ) )
                            // InternalGaml.g:5331:6: ( (lv_op_2_0= '#' ) ) ( (lv_right_3_0= ruleUnitRef ) )
                            {
                            // InternalGaml.g:5331:6: ( (lv_op_2_0= '#' ) )
                            // InternalGaml.g:5332:7: (lv_op_2_0= '#' )
                            {
                            // InternalGaml.g:5332:7: (lv_op_2_0= '#' )
                            // InternalGaml.g:5333:8: lv_op_2_0= '#'
                            {
                            lv_op_2_0=(Token)match(input,116,FOLLOW_7); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								newLeafNode(lv_op_2_0, grammarAccess.getUnaryAccess().getOpNumberSignKeyword_1_1_0_0_0());
                              							
                            }
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElement(grammarAccess.getUnaryRule());
                              								}
                              								setWithLastConsumed(current, "op", lv_op_2_0, "#");
                              							
                            }

                            }


                            }

                            // InternalGaml.g:5345:6: ( (lv_right_3_0= ruleUnitRef ) )
                            // InternalGaml.g:5346:7: (lv_right_3_0= ruleUnitRef )
                            {
                            // InternalGaml.g:5346:7: (lv_right_3_0= ruleUnitRef )
                            // InternalGaml.g:5347:8: lv_right_3_0= ruleUnitRef
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getUnaryAccess().getRightUnitRefParserRuleCall_1_1_0_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_3_0=ruleUnitRef();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getUnaryRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_3_0,
                              									"gama.core.lang.Gaml.UnitRef");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;
                        case 2 :
                            // InternalGaml.g:5366:5: ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) )
                            {
                            // InternalGaml.g:5366:5: ( ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) ) )
                            // InternalGaml.g:5367:6: ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) ) ( (lv_right_5_0= ruleUnary ) )
                            {
                            // InternalGaml.g:5367:6: ( ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) ) )
                            // InternalGaml.g:5368:7: ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) )
                            {
                            // InternalGaml.g:5368:7: ( (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' ) )
                            // InternalGaml.g:5369:8: (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' )
                            {
                            // InternalGaml.g:5369:8: (lv_op_4_1= '-' | lv_op_4_2= '!' | lv_op_4_3= 'not' )
                            int alt92=3;
                            switch ( input.LA(1) ) {
                            case 112:
                                {
                                alt92=1;
                                }
                                break;
                            case 117:
                                {
                                alt92=2;
                                }
                                break;
                            case 118:
                                {
                                alt92=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return current;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 92, 0, input);

                                throw nvae;
                            }

                            switch (alt92) {
                                case 1 :
                                    // InternalGaml.g:5370:9: lv_op_4_1= '-'
                                    {
                                    lv_op_4_1=(Token)match(input,112,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_1, grammarAccess.getUnaryAccess().getOpHyphenMinusKeyword_1_1_1_0_0_0());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_1, null);
                                      								
                                    }

                                    }
                                    break;
                                case 2 :
                                    // InternalGaml.g:5381:9: lv_op_4_2= '!'
                                    {
                                    lv_op_4_2=(Token)match(input,117,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_2, grammarAccess.getUnaryAccess().getOpExclamationMarkKeyword_1_1_1_0_0_1());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_2, null);
                                      								
                                    }

                                    }
                                    break;
                                case 3 :
                                    // InternalGaml.g:5392:9: lv_op_4_3= 'not'
                                    {
                                    lv_op_4_3=(Token)match(input,118,FOLLOW_5); if (state.failed) return current;
                                    if ( state.backtracking==0 ) {

                                      									newLeafNode(lv_op_4_3, grammarAccess.getUnaryAccess().getOpNotKeyword_1_1_1_0_0_2());
                                      								
                                    }
                                    if ( state.backtracking==0 ) {

                                      									if (current==null) {
                                      										current = createModelElement(grammarAccess.getUnaryRule());
                                      									}
                                      									setWithLastConsumed(current, "op", lv_op_4_3, null);
                                      								
                                    }

                                    }
                                    break;

                            }


                            }


                            }

                            // InternalGaml.g:5405:6: ( (lv_right_5_0= ruleUnary ) )
                            // InternalGaml.g:5406:7: (lv_right_5_0= ruleUnary )
                            {
                            // InternalGaml.g:5406:7: (lv_right_5_0= ruleUnary )
                            // InternalGaml.g:5407:8: lv_right_5_0= ruleUnary
                            {
                            if ( state.backtracking==0 ) {

                              								newCompositeNode(grammarAccess.getUnaryAccess().getRightUnaryParserRuleCall_1_1_1_1_0());
                              							
                            }
                            pushFollow(FOLLOW_2);
                            lv_right_5_0=ruleUnary();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              								if (current==null) {
                              									current = createModelElementForParent(grammarAccess.getUnaryRule());
                              								}
                              								set(
                              									current,
                              									"right",
                              									lv_right_5_0,
                              									"gama.core.lang.Gaml.Unary");
                              								afterParserOrEnumRuleCall();
                              							
                            }

                            }


                            }


                            }


                            }
                            break;

                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnary"


    // $ANTLR start "entryRuleAccess"
    // InternalGaml.g:5431:1: entryRuleAccess returns [EObject current=null] : iv_ruleAccess= ruleAccess EOF ;
    public final EObject entryRuleAccess() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAccess = null;


        try {
            // InternalGaml.g:5431:47: (iv_ruleAccess= ruleAccess EOF )
            // InternalGaml.g:5432:2: iv_ruleAccess= ruleAccess EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAccessRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAccess=ruleAccess();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAccess; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAccess"


    // $ANTLR start "ruleAccess"
    // InternalGaml.g:5438:1: ruleAccess returns [EObject current=null] : (this_Primary_0= rulePrimary ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )* ) ;
    public final EObject ruleAccess() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token otherlv_4=null;
        Token lv_op_5_0=null;
        EObject this_Primary_0 = null;

        EObject lv_right_3_0 = null;

        EObject lv_right_6_1 = null;

        EObject lv_right_6_2 = null;



        	enterRule();

        try {
            // InternalGaml.g:5444:2: ( (this_Primary_0= rulePrimary ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )* ) )
            // InternalGaml.g:5445:2: (this_Primary_0= rulePrimary ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )* )
            {
            // InternalGaml.g:5445:2: (this_Primary_0= rulePrimary ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )* )
            // InternalGaml.g:5446:3: this_Primary_0= rulePrimary ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )*
            {
            if ( state.backtracking==0 ) {

              			newCompositeNode(grammarAccess.getAccessAccess().getPrimaryParserRuleCall_0());
              		
            }
            pushFollow(FOLLOW_49);
            this_Primary_0=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			current = this_Primary_0;
              			afterParserOrEnumRuleCall();
              		
            }
            // InternalGaml.g:5454:3: ( () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) ) )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==119||LA98_0==121) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // InternalGaml.g:5455:4: () ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) )
            	    {
            	    // InternalGaml.g:5455:4: ()
            	    // InternalGaml.g:5456:5: 
            	    {
            	    if ( state.backtracking==0 ) {

            	      					current = forceCreateModelElementAndSet(
            	      						grammarAccess.getAccessAccess().getAccessLeftAction_1_0(),
            	      						current);
            	      				
            	    }

            	    }

            	    // InternalGaml.g:5462:4: ( ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' ) | ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) ) )
            	    int alt97=2;
            	    int LA97_0 = input.LA(1);

            	    if ( (LA97_0==119) ) {
            	        alt97=1;
            	    }
            	    else if ( (LA97_0==121) ) {
            	        alt97=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return current;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 97, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt97) {
            	        case 1 :
            	            // InternalGaml.g:5463:5: ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' )
            	            {
            	            // InternalGaml.g:5463:5: ( ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']' )
            	            // InternalGaml.g:5464:6: ( (lv_op_2_0= '[' ) ) ( (lv_right_3_0= ruleExpressionList ) )? otherlv_4= ']'
            	            {
            	            // InternalGaml.g:5464:6: ( (lv_op_2_0= '[' ) )
            	            // InternalGaml.g:5465:7: (lv_op_2_0= '[' )
            	            {
            	            // InternalGaml.g:5465:7: (lv_op_2_0= '[' )
            	            // InternalGaml.g:5466:8: lv_op_2_0= '['
            	            {
            	            lv_op_2_0=(Token)match(input,119,FOLLOW_50); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_2_0, grammarAccess.getAccessAccess().getOpLeftSquareBracketKeyword_1_1_0_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAccessRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_2_0, "[");
            	              							
            	            }

            	            }


            	            }

            	            // InternalGaml.g:5478:6: ( (lv_right_3_0= ruleExpressionList ) )?
            	            int alt95=2;
            	            int LA95_0 = input.LA(1);

            	            if ( ((LA95_0>=RULE_ID && LA95_0<=RULE_KEYWORD)||LA95_0==29||LA95_0==32||LA95_0==36||(LA95_0>=38 && LA95_0<=81)||(LA95_0>=90 && LA95_0<=101)||LA95_0==112||(LA95_0>=116 && LA95_0<=119)) ) {
            	                alt95=1;
            	            }
            	            switch (alt95) {
            	                case 1 :
            	                    // InternalGaml.g:5479:7: (lv_right_3_0= ruleExpressionList )
            	                    {
            	                    // InternalGaml.g:5479:7: (lv_right_3_0= ruleExpressionList )
            	                    // InternalGaml.g:5480:8: lv_right_3_0= ruleExpressionList
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      								newCompositeNode(grammarAccess.getAccessAccess().getRightExpressionListParserRuleCall_1_1_0_1_0());
            	                      							
            	                    }
            	                    pushFollow(FOLLOW_51);
            	                    lv_right_3_0=ruleExpressionList();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      								if (current==null) {
            	                      									current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      								}
            	                      								set(
            	                      									current,
            	                      									"right",
            	                      									lv_right_3_0,
            	                      									"gama.core.lang.Gaml.ExpressionList");
            	                      								afterParserOrEnumRuleCall();
            	                      							
            	                    }

            	                    }


            	                    }
            	                    break;

            	            }

            	            otherlv_4=(Token)match(input,120,FOLLOW_49); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              						newLeafNode(otherlv_4, grammarAccess.getAccessAccess().getRightSquareBracketKeyword_1_1_0_2());
            	              					
            	            }

            	            }


            	            }
            	            break;
            	        case 2 :
            	            // InternalGaml.g:5503:5: ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) )
            	            {
            	            // InternalGaml.g:5503:5: ( ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) ) )
            	            // InternalGaml.g:5504:6: ( (lv_op_5_0= '.' ) ) ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) )
            	            {
            	            // InternalGaml.g:5504:6: ( (lv_op_5_0= '.' ) )
            	            // InternalGaml.g:5505:7: (lv_op_5_0= '.' )
            	            {
            	            // InternalGaml.g:5505:7: (lv_op_5_0= '.' )
            	            // InternalGaml.g:5506:8: lv_op_5_0= '.'
            	            {
            	            lv_op_5_0=(Token)match(input,121,FOLLOW_13); if (state.failed) return current;
            	            if ( state.backtracking==0 ) {

            	              								newLeafNode(lv_op_5_0, grammarAccess.getAccessAccess().getOpFullStopKeyword_1_1_1_0_0());
            	              							
            	            }
            	            if ( state.backtracking==0 ) {

            	              								if (current==null) {
            	              									current = createModelElement(grammarAccess.getAccessRule());
            	              								}
            	              								setWithLastConsumed(current, "op", lv_op_5_0, ".");
            	              							
            	            }

            	            }


            	            }

            	            // InternalGaml.g:5518:6: ( ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) ) )
            	            // InternalGaml.g:5519:7: ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) )
            	            {
            	            // InternalGaml.g:5519:7: ( (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral ) )
            	            // InternalGaml.g:5520:8: (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral )
            	            {
            	            // InternalGaml.g:5520:8: (lv_right_6_1= ruleAbstractRef | lv_right_6_2= ruleStringLiteral )
            	            int alt96=2;
            	            int LA96_0 = input.LA(1);

            	            if ( (LA96_0==RULE_ID||LA96_0==36||(LA96_0>=38 && LA96_0<=81)) ) {
            	                alt96=1;
            	            }
            	            else if ( (LA96_0==RULE_STRING) ) {
            	                alt96=2;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return current;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 96, 0, input);

            	                throw nvae;
            	            }
            	            switch (alt96) {
            	                case 1 :
            	                    // InternalGaml.g:5521:9: lv_right_6_1= ruleAbstractRef
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      									newCompositeNode(grammarAccess.getAccessAccess().getRightAbstractRefParserRuleCall_1_1_1_1_0_0());
            	                      								
            	                    }
            	                    pushFollow(FOLLOW_49);
            	                    lv_right_6_1=ruleAbstractRef();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      									if (current==null) {
            	                      										current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      									}
            	                      									set(
            	                      										current,
            	                      										"right",
            	                      										lv_right_6_1,
            	                      										"gama.core.lang.Gaml.AbstractRef");
            	                      									afterParserOrEnumRuleCall();
            	                      								
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    // InternalGaml.g:5537:9: lv_right_6_2= ruleStringLiteral
            	                    {
            	                    if ( state.backtracking==0 ) {

            	                      									newCompositeNode(grammarAccess.getAccessAccess().getRightStringLiteralParserRuleCall_1_1_1_1_0_1());
            	                      								
            	                    }
            	                    pushFollow(FOLLOW_49);
            	                    lv_right_6_2=ruleStringLiteral();

            	                    state._fsp--;
            	                    if (state.failed) return current;
            	                    if ( state.backtracking==0 ) {

            	                      									if (current==null) {
            	                      										current = createModelElementForParent(grammarAccess.getAccessRule());
            	                      									}
            	                      									set(
            	                      										current,
            	                      										"right",
            	                      										lv_right_6_2,
            	                      										"gama.core.lang.Gaml.StringLiteral");
            	                      									afterParserOrEnumRuleCall();
            	                      								
            	                    }

            	                    }
            	                    break;

            	            }


            	            }


            	            }


            	            }


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop98;
                }
            } while (true);


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAccess"


    // $ANTLR start "entryRulePrimary"
    // InternalGaml.g:5562:1: entryRulePrimary returns [EObject current=null] : iv_rulePrimary= rulePrimary EOF ;
    public final EObject entryRulePrimary() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePrimary = null;


        try {
            // InternalGaml.g:5562:48: (iv_rulePrimary= rulePrimary EOF )
            // InternalGaml.g:5563:2: iv_rulePrimary= rulePrimary EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getPrimaryRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_rulePrimary=rulePrimary();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_rulePrimary; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePrimary"


    // $ANTLR start "rulePrimary"
    // InternalGaml.g:5569:1: rulePrimary returns [EObject current=null] : (this_TerminalExpression_0= ruleTerminalExpression | this_AbstractRef_1= ruleAbstractRef | (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' ) | (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' ) | (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' ) ) ;
    public final EObject rulePrimary() throws RecognitionException {
        EObject current = null;

        Token otherlv_2=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_op_12_0=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        EObject this_TerminalExpression_0 = null;

        EObject this_AbstractRef_1 = null;

        EObject this_ExpressionList_3 = null;

        EObject lv_exprs_7_0 = null;

        EObject lv_left_11_0 = null;

        EObject lv_right_13_0 = null;

        EObject lv_z_15_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5575:2: ( (this_TerminalExpression_0= ruleTerminalExpression | this_AbstractRef_1= ruleAbstractRef | (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' ) | (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' ) | (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' ) ) )
            // InternalGaml.g:5576:2: (this_TerminalExpression_0= ruleTerminalExpression | this_AbstractRef_1= ruleAbstractRef | (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' ) | (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' ) | (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' ) )
            {
            // InternalGaml.g:5576:2: (this_TerminalExpression_0= ruleTerminalExpression | this_AbstractRef_1= ruleAbstractRef | (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' ) | (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' ) | (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' ) )
            int alt101=5;
            switch ( input.LA(1) ) {
            case RULE_STRING:
            case RULE_INTEGER:
            case RULE_DOUBLE:
            case RULE_BOOLEAN:
            case RULE_KEYWORD:
                {
                alt101=1;
                }
                break;
            case RULE_ID:
            case 36:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
                {
                alt101=2;
                }
                break;
            case 29:
                {
                alt101=3;
                }
                break;
            case 119:
                {
                alt101=4;
                }
                break;
            case 32:
                {
                alt101=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // InternalGaml.g:5577:3: this_TerminalExpression_0= ruleTerminalExpression
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryAccess().getTerminalExpressionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TerminalExpression_0=ruleTerminalExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TerminalExpression_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:5586:3: this_AbstractRef_1= ruleAbstractRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getPrimaryAccess().getAbstractRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_AbstractRef_1=ruleAbstractRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_AbstractRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:5595:3: (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' )
                    {
                    // InternalGaml.g:5595:3: (otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')' )
                    // InternalGaml.g:5596:4: otherlv_2= '(' this_ExpressionList_3= ruleExpressionList otherlv_4= ')'
                    {
                    otherlv_2=(Token)match(input,29,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getPrimaryAccess().getLeftParenthesisKeyword_2_0());
                      			
                    }
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getPrimaryAccess().getExpressionListParserRuleCall_2_1());
                      			
                    }
                    pushFollow(FOLLOW_24);
                    this_ExpressionList_3=ruleExpressionList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_ExpressionList_3;
                      				afterParserOrEnumRuleCall();
                      			
                    }
                    otherlv_4=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_4, grammarAccess.getPrimaryAccess().getRightParenthesisKeyword_2_2());
                      			
                    }

                    }


                    }
                    break;
                case 4 :
                    // InternalGaml.g:5614:3: (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' )
                    {
                    // InternalGaml.g:5614:3: (otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']' )
                    // InternalGaml.g:5615:4: otherlv_5= '[' () ( (lv_exprs_7_0= ruleExpressionList ) )? otherlv_8= ']'
                    {
                    otherlv_5=(Token)match(input,119,FOLLOW_50); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_5, grammarAccess.getPrimaryAccess().getLeftSquareBracketKeyword_3_0());
                      			
                    }
                    // InternalGaml.g:5619:4: ()
                    // InternalGaml.g:5620:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryAccess().getArrayAction_3_1(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:5626:4: ( (lv_exprs_7_0= ruleExpressionList ) )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( ((LA99_0>=RULE_ID && LA99_0<=RULE_KEYWORD)||LA99_0==29||LA99_0==32||LA99_0==36||(LA99_0>=38 && LA99_0<=81)||(LA99_0>=90 && LA99_0<=101)||LA99_0==112||(LA99_0>=116 && LA99_0<=119)) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // InternalGaml.g:5627:5: (lv_exprs_7_0= ruleExpressionList )
                            {
                            // InternalGaml.g:5627:5: (lv_exprs_7_0= ruleExpressionList )
                            // InternalGaml.g:5628:6: lv_exprs_7_0= ruleExpressionList
                            {
                            if ( state.backtracking==0 ) {

                              						newCompositeNode(grammarAccess.getPrimaryAccess().getExprsExpressionListParserRuleCall_3_2_0());
                              					
                            }
                            pushFollow(FOLLOW_51);
                            lv_exprs_7_0=ruleExpressionList();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              						if (current==null) {
                              							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                              						}
                              						set(
                              							current,
                              							"exprs",
                              							lv_exprs_7_0,
                              							"gama.core.lang.Gaml.ExpressionList");
                              						afterParserOrEnumRuleCall();
                              					
                            }

                            }


                            }
                            break;

                    }

                    otherlv_8=(Token)match(input,120,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_8, grammarAccess.getPrimaryAccess().getRightSquareBracketKeyword_3_3());
                      			
                    }

                    }


                    }
                    break;
                case 5 :
                    // InternalGaml.g:5651:3: (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' )
                    {
                    // InternalGaml.g:5651:3: (otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}' )
                    // InternalGaml.g:5652:4: otherlv_9= '{' () ( (lv_left_11_0= ruleExpression ) ) ( (lv_op_12_0= ',' ) ) ( (lv_right_13_0= ruleExpression ) ) (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )? otherlv_16= '}'
                    {
                    otherlv_9=(Token)match(input,32,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_9, grammarAccess.getPrimaryAccess().getLeftCurlyBracketKeyword_4_0());
                      			
                    }
                    // InternalGaml.g:5656:4: ()
                    // InternalGaml.g:5657:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getPrimaryAccess().getPointAction_4_1(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:5663:4: ( (lv_left_11_0= ruleExpression ) )
                    // InternalGaml.g:5664:5: (lv_left_11_0= ruleExpression )
                    {
                    // InternalGaml.g:5664:5: (lv_left_11_0= ruleExpression )
                    // InternalGaml.g:5665:6: lv_left_11_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimaryAccess().getLeftExpressionParserRuleCall_4_2_0());
                      					
                    }
                    pushFollow(FOLLOW_52);
                    lv_left_11_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                      						}
                      						set(
                      							current,
                      							"left",
                      							lv_left_11_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGaml.g:5682:4: ( (lv_op_12_0= ',' ) )
                    // InternalGaml.g:5683:5: (lv_op_12_0= ',' )
                    {
                    // InternalGaml.g:5683:5: (lv_op_12_0= ',' )
                    // InternalGaml.g:5684:6: lv_op_12_0= ','
                    {
                    lv_op_12_0=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_12_0, grammarAccess.getPrimaryAccess().getOpCommaKeyword_4_3_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getPrimaryRule());
                      						}
                      						setWithLastConsumed(current, "op", lv_op_12_0, ",");
                      					
                    }

                    }


                    }

                    // InternalGaml.g:5696:4: ( (lv_right_13_0= ruleExpression ) )
                    // InternalGaml.g:5697:5: (lv_right_13_0= ruleExpression )
                    {
                    // InternalGaml.g:5697:5: (lv_right_13_0= ruleExpression )
                    // InternalGaml.g:5698:6: lv_right_13_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getPrimaryAccess().getRightExpressionParserRuleCall_4_4_0());
                      					
                    }
                    pushFollow(FOLLOW_53);
                    lv_right_13_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getPrimaryRule());
                      						}
                      						set(
                      							current,
                      							"right",
                      							lv_right_13_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGaml.g:5715:4: (otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) ) )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==88) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // InternalGaml.g:5716:5: otherlv_14= ',' ( (lv_z_15_0= ruleExpression ) )
                            {
                            otherlv_14=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					newLeafNode(otherlv_14, grammarAccess.getPrimaryAccess().getCommaKeyword_4_5_0());
                              				
                            }
                            // InternalGaml.g:5720:5: ( (lv_z_15_0= ruleExpression ) )
                            // InternalGaml.g:5721:6: (lv_z_15_0= ruleExpression )
                            {
                            // InternalGaml.g:5721:6: (lv_z_15_0= ruleExpression )
                            // InternalGaml.g:5722:7: lv_z_15_0= ruleExpression
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getPrimaryAccess().getZExpressionParserRuleCall_4_5_1_0());
                              						
                            }
                            pushFollow(FOLLOW_54);
                            lv_z_15_0=ruleExpression();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getPrimaryRule());
                              							}
                              							set(
                              								current,
                              								"z",
                              								lv_z_15_0,
                              								"gama.core.lang.Gaml.Expression");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }


                            }
                            break;

                    }

                    otherlv_16=(Token)match(input,33,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_16, grammarAccess.getPrimaryAccess().getRightCurlyBracketKeyword_4_6());
                      			
                    }

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePrimary"


    // $ANTLR start "entryRuleAbstractRef"
    // InternalGaml.g:5749:1: entryRuleAbstractRef returns [EObject current=null] : iv_ruleAbstractRef= ruleAbstractRef EOF ;
    public final EObject entryRuleAbstractRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAbstractRef = null;


        try {
            // InternalGaml.g:5749:52: (iv_ruleAbstractRef= ruleAbstractRef EOF )
            // InternalGaml.g:5750:2: iv_ruleAbstractRef= ruleAbstractRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getAbstractRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleAbstractRef=ruleAbstractRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleAbstractRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAbstractRef"


    // $ANTLR start "ruleAbstractRef"
    // InternalGaml.g:5756:1: ruleAbstractRef returns [EObject current=null] : ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef ) ;
    public final EObject ruleAbstractRef() throws RecognitionException {
        EObject current = null;

        EObject this_Function_0 = null;

        EObject this_VariableRef_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:5762:2: ( ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef ) )
            // InternalGaml.g:5763:2: ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef )
            {
            // InternalGaml.g:5763:2: ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef )
            int alt102=2;
            alt102 = dfa102.predict(input);
            switch (alt102) {
                case 1 :
                    // InternalGaml.g:5764:3: ( ( ruleFunction )=>this_Function_0= ruleFunction )
                    {
                    // InternalGaml.g:5764:3: ( ( ruleFunction )=>this_Function_0= ruleFunction )
                    // InternalGaml.g:5765:4: ( ruleFunction )=>this_Function_0= ruleFunction
                    {
                    if ( state.backtracking==0 ) {

                      				newCompositeNode(grammarAccess.getAbstractRefAccess().getFunctionParserRuleCall_0());
                      			
                    }
                    pushFollow(FOLLOW_2);
                    this_Function_0=ruleFunction();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				current = this_Function_0;
                      				afterParserOrEnumRuleCall();
                      			
                    }

                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:5776:3: this_VariableRef_1= ruleVariableRef
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getAbstractRefAccess().getVariableRefParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_VariableRef_1=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_VariableRef_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAbstractRef"


    // $ANTLR start "entryRuleFunction"
    // InternalGaml.g:5788:1: entryRuleFunction returns [EObject current=null] : iv_ruleFunction= ruleFunction EOF ;
    public final EObject entryRuleFunction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFunction = null;


        try {
            // InternalGaml.g:5788:49: (iv_ruleFunction= ruleFunction EOF )
            // InternalGaml.g:5789:2: iv_ruleFunction= ruleFunction EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getFunctionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleFunction=ruleFunction();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleFunction; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFunction"


    // $ANTLR start "ruleFunction"
    // InternalGaml.g:5795:1: ruleFunction returns [EObject current=null] : ( () ( (lv_left_1_0= ruleActionRef ) ) ( (lv_type_2_0= ruleTypeInfo ) )? otherlv_3= '(' ( (lv_right_4_0= ruleExpressionList ) )? otherlv_5= ')' ) ;
    public final EObject ruleFunction() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        Token otherlv_5=null;
        EObject lv_left_1_0 = null;

        EObject lv_type_2_0 = null;

        EObject lv_right_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5801:2: ( ( () ( (lv_left_1_0= ruleActionRef ) ) ( (lv_type_2_0= ruleTypeInfo ) )? otherlv_3= '(' ( (lv_right_4_0= ruleExpressionList ) )? otherlv_5= ')' ) )
            // InternalGaml.g:5802:2: ( () ( (lv_left_1_0= ruleActionRef ) ) ( (lv_type_2_0= ruleTypeInfo ) )? otherlv_3= '(' ( (lv_right_4_0= ruleExpressionList ) )? otherlv_5= ')' )
            {
            // InternalGaml.g:5802:2: ( () ( (lv_left_1_0= ruleActionRef ) ) ( (lv_type_2_0= ruleTypeInfo ) )? otherlv_3= '(' ( (lv_right_4_0= ruleExpressionList ) )? otherlv_5= ')' )
            // InternalGaml.g:5803:3: () ( (lv_left_1_0= ruleActionRef ) ) ( (lv_type_2_0= ruleTypeInfo ) )? otherlv_3= '(' ( (lv_right_4_0= ruleExpressionList ) )? otherlv_5= ')'
            {
            // InternalGaml.g:5803:3: ()
            // InternalGaml.g:5804:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getFunctionAccess().getFunctionAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:5810:3: ( (lv_left_1_0= ruleActionRef ) )
            // InternalGaml.g:5811:4: (lv_left_1_0= ruleActionRef )
            {
            // InternalGaml.g:5811:4: (lv_left_1_0= ruleActionRef )
            // InternalGaml.g:5812:5: lv_left_1_0= ruleActionRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getFunctionAccess().getLeftActionRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_55);
            lv_left_1_0=ruleActionRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getFunctionRule());
              					}
              					set(
              						current,
              						"left",
              						lv_left_1_0,
              						"gama.core.lang.Gaml.ActionRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:5829:3: ( (lv_type_2_0= ruleTypeInfo ) )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==110) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // InternalGaml.g:5830:4: (lv_type_2_0= ruleTypeInfo )
                    {
                    // InternalGaml.g:5830:4: (lv_type_2_0= ruleTypeInfo )
                    // InternalGaml.g:5831:5: lv_type_2_0= ruleTypeInfo
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getFunctionAccess().getTypeTypeInfoParserRuleCall_2_0());
                      				
                    }
                    pushFollow(FOLLOW_56);
                    lv_type_2_0=ruleTypeInfo();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getFunctionRule());
                      					}
                      					set(
                      						current,
                      						"type",
                      						lv_type_2_0,
                      						"gama.core.lang.Gaml.TypeInfo");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_3=(Token)match(input,29,FOLLOW_57); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_3, grammarAccess.getFunctionAccess().getLeftParenthesisKeyword_3());
              		
            }
            // InternalGaml.g:5852:3: ( (lv_right_4_0= ruleExpressionList ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=RULE_ID && LA104_0<=RULE_KEYWORD)||LA104_0==29||LA104_0==32||LA104_0==36||(LA104_0>=38 && LA104_0<=81)||(LA104_0>=90 && LA104_0<=101)||LA104_0==112||(LA104_0>=116 && LA104_0<=119)) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalGaml.g:5853:4: (lv_right_4_0= ruleExpressionList )
                    {
                    // InternalGaml.g:5853:4: (lv_right_4_0= ruleExpressionList )
                    // InternalGaml.g:5854:5: lv_right_4_0= ruleExpressionList
                    {
                    if ( state.backtracking==0 ) {

                      					newCompositeNode(grammarAccess.getFunctionAccess().getRightExpressionListParserRuleCall_4_0());
                      				
                    }
                    pushFollow(FOLLOW_24);
                    lv_right_4_0=ruleExpressionList();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					if (current==null) {
                      						current = createModelElementForParent(grammarAccess.getFunctionRule());
                      					}
                      					set(
                      						current,
                      						"right",
                      						lv_right_4_0,
                      						"gama.core.lang.Gaml.ExpressionList");
                      					afterParserOrEnumRuleCall();
                      				
                    }

                    }


                    }
                    break;

            }

            otherlv_5=(Token)match(input,30,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_5, grammarAccess.getFunctionAccess().getRightParenthesisKeyword_5());
              		
            }

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFunction"


    // $ANTLR start "entryRuleExpressionList"
    // InternalGaml.g:5879:1: entryRuleExpressionList returns [EObject current=null] : iv_ruleExpressionList= ruleExpressionList EOF ;
    public final EObject entryRuleExpressionList() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExpressionList = null;


        try {
            // InternalGaml.g:5879:55: (iv_ruleExpressionList= ruleExpressionList EOF )
            // InternalGaml.g:5880:2: iv_ruleExpressionList= ruleExpressionList EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getExpressionListRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleExpressionList=ruleExpressionList();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleExpressionList; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExpressionList"


    // $ANTLR start "ruleExpressionList"
    // InternalGaml.g:5886:1: ruleExpressionList returns [EObject current=null] : ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) ) ;
    public final EObject ruleExpressionList() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_exprs_0_0 = null;

        EObject lv_exprs_2_0 = null;

        EObject lv_exprs_3_0 = null;

        EObject lv_exprs_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:5892:2: ( ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) ) )
            // InternalGaml.g:5893:2: ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) )
            {
            // InternalGaml.g:5893:2: ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) )
            int alt107=2;
            alt107 = dfa107.predict(input);
            switch (alt107) {
                case 1 :
                    // InternalGaml.g:5894:3: ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* )
                    {
                    // InternalGaml.g:5894:3: ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* )
                    // InternalGaml.g:5895:4: ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )*
                    {
                    // InternalGaml.g:5895:4: ( (lv_exprs_0_0= ruleExpression ) )
                    // InternalGaml.g:5896:5: (lv_exprs_0_0= ruleExpression )
                    {
                    // InternalGaml.g:5896:5: (lv_exprs_0_0= ruleExpression )
                    // InternalGaml.g:5897:6: lv_exprs_0_0= ruleExpression
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getExpressionListAccess().getExprsExpressionParserRuleCall_0_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_exprs_0_0=ruleExpression();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getExpressionListRule());
                      						}
                      						add(
                      							current,
                      							"exprs",
                      							lv_exprs_0_0,
                      							"gama.core.lang.Gaml.Expression");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGaml.g:5914:4: (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )*
                    loop105:
                    do {
                        int alt105=2;
                        int LA105_0 = input.LA(1);

                        if ( (LA105_0==88) ) {
                            alt105=1;
                        }


                        switch (alt105) {
                    	case 1 :
                    	    // InternalGaml.g:5915:5: otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) )
                    	    {
                    	    otherlv_1=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_1, grammarAccess.getExpressionListAccess().getCommaKeyword_0_1_0());
                    	      				
                    	    }
                    	    // InternalGaml.g:5919:5: ( (lv_exprs_2_0= ruleExpression ) )
                    	    // InternalGaml.g:5920:6: (lv_exprs_2_0= ruleExpression )
                    	    {
                    	    // InternalGaml.g:5920:6: (lv_exprs_2_0= ruleExpression )
                    	    // InternalGaml.g:5921:7: lv_exprs_2_0= ruleExpression
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getExpressionListAccess().getExprsExpressionParserRuleCall_0_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_32);
                    	    lv_exprs_2_0=ruleExpression();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"exprs",
                    	      								lv_exprs_2_0,
                    	      								"gama.core.lang.Gaml.Expression");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop105;
                        }
                    } while (true);


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:5941:3: ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* )
                    {
                    // InternalGaml.g:5941:3: ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* )
                    // InternalGaml.g:5942:4: ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )*
                    {
                    // InternalGaml.g:5942:4: ( (lv_exprs_3_0= ruleParameter ) )
                    // InternalGaml.g:5943:5: (lv_exprs_3_0= ruleParameter )
                    {
                    // InternalGaml.g:5943:5: (lv_exprs_3_0= ruleParameter )
                    // InternalGaml.g:5944:6: lv_exprs_3_0= ruleParameter
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getExpressionListAccess().getExprsParameterParserRuleCall_1_0_0());
                      					
                    }
                    pushFollow(FOLLOW_32);
                    lv_exprs_3_0=ruleParameter();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getExpressionListRule());
                      						}
                      						add(
                      							current,
                      							"exprs",
                      							lv_exprs_3_0,
                      							"gama.core.lang.Gaml.Parameter");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }

                    // InternalGaml.g:5961:4: (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )*
                    loop106:
                    do {
                        int alt106=2;
                        int LA106_0 = input.LA(1);

                        if ( (LA106_0==88) ) {
                            alt106=1;
                        }


                        switch (alt106) {
                    	case 1 :
                    	    // InternalGaml.g:5962:5: otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) )
                    	    {
                    	    otherlv_4=(Token)match(input,88,FOLLOW_5); if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      					newLeafNode(otherlv_4, grammarAccess.getExpressionListAccess().getCommaKeyword_1_1_0());
                    	      				
                    	    }
                    	    // InternalGaml.g:5966:5: ( (lv_exprs_5_0= ruleParameter ) )
                    	    // InternalGaml.g:5967:6: (lv_exprs_5_0= ruleParameter )
                    	    {
                    	    // InternalGaml.g:5967:6: (lv_exprs_5_0= ruleParameter )
                    	    // InternalGaml.g:5968:7: lv_exprs_5_0= ruleParameter
                    	    {
                    	    if ( state.backtracking==0 ) {

                    	      							newCompositeNode(grammarAccess.getExpressionListAccess().getExprsParameterParserRuleCall_1_1_1_0());
                    	      						
                    	    }
                    	    pushFollow(FOLLOW_32);
                    	    lv_exprs_5_0=ruleParameter();

                    	    state._fsp--;
                    	    if (state.failed) return current;
                    	    if ( state.backtracking==0 ) {

                    	      							if (current==null) {
                    	      								current = createModelElementForParent(grammarAccess.getExpressionListRule());
                    	      							}
                    	      							add(
                    	      								current,
                    	      								"exprs",
                    	      								lv_exprs_5_0,
                    	      								"gama.core.lang.Gaml.Parameter");
                    	      							afterParserOrEnumRuleCall();
                    	      						
                    	    }

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop106;
                        }
                    } while (true);


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExpressionList"


    // $ANTLR start "entryRuleParameter"
    // InternalGaml.g:5991:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalGaml.g:5991:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalGaml.g:5992:2: iv_ruleParameter= ruleParameter EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getParameterRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleParameter; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalGaml.g:5998:1: ruleParameter returns [EObject current=null] : ( () ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) ) ( (lv_right_4_0= ruleExpression ) ) ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token otherlv_3=null;
        AntlrDatatypeRuleToken lv_builtInFacetKey_1_1 = null;

        AntlrDatatypeRuleToken lv_builtInFacetKey_1_2 = null;

        AntlrDatatypeRuleToken lv_builtInFacetKey_1_3 = null;

        EObject lv_left_2_0 = null;

        EObject lv_right_4_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6004:2: ( ( () ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) ) ( (lv_right_4_0= ruleExpression ) ) ) )
            // InternalGaml.g:6005:2: ( () ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) ) ( (lv_right_4_0= ruleExpression ) ) )
            {
            // InternalGaml.g:6005:2: ( () ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) ) ( (lv_right_4_0= ruleExpression ) ) )
            // InternalGaml.g:6006:3: () ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) ) ( (lv_right_4_0= ruleExpression ) )
            {
            // InternalGaml.g:6006:3: ()
            // InternalGaml.g:6007:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getParameterAccess().getParameterAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:6013:3: ( ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) ) | ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' ) )
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( ((LA109_0>=90 && LA109_0<=101)) ) {
                alt109=1;
            }
            else if ( (LA109_0==RULE_ID||LA109_0==36||(LA109_0>=38 && LA109_0<=81)) ) {
                alt109=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }
            switch (alt109) {
                case 1 :
                    // InternalGaml.g:6014:4: ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) )
                    {
                    // InternalGaml.g:6014:4: ( ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) ) )
                    // InternalGaml.g:6015:5: ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) )
                    {
                    // InternalGaml.g:6015:5: ( (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey ) )
                    // InternalGaml.g:6016:6: (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey )
                    {
                    // InternalGaml.g:6016:6: (lv_builtInFacetKey_1_1= ruleDefinitionFacetKey | lv_builtInFacetKey_1_2= ruleSpecialFacetKey | lv_builtInFacetKey_1_3= ruleActionFacetKey )
                    int alt108=3;
                    switch ( input.LA(1) ) {
                    case 90:
                        {
                        alt108=1;
                        }
                        break;
                    case 91:
                    case 92:
                    case 93:
                    case 94:
                    case 95:
                    case 96:
                    case 97:
                    case 98:
                    case 99:
                        {
                        alt108=2;
                        }
                        break;
                    case 100:
                    case 101:
                        {
                        alt108=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 108, 0, input);

                        throw nvae;
                    }

                    switch (alt108) {
                        case 1 :
                            // InternalGaml.g:6017:7: lv_builtInFacetKey_1_1= ruleDefinitionFacetKey
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeyDefinitionFacetKeyParserRuleCall_1_0_0_0());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_1=ruleDefinitionFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_1,
                              								"gama.core.lang.Gaml.DefinitionFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 2 :
                            // InternalGaml.g:6033:7: lv_builtInFacetKey_1_2= ruleSpecialFacetKey
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeySpecialFacetKeyParserRuleCall_1_0_0_1());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_2=ruleSpecialFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_2,
                              								"gama.core.lang.Gaml.SpecialFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;
                        case 3 :
                            // InternalGaml.g:6049:7: lv_builtInFacetKey_1_3= ruleActionFacetKey
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getParameterAccess().getBuiltInFacetKeyActionFacetKeyParserRuleCall_1_0_0_2());
                              						
                            }
                            pushFollow(FOLLOW_5);
                            lv_builtInFacetKey_1_3=ruleActionFacetKey();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getParameterRule());
                              							}
                              							set(
                              								current,
                              								"builtInFacetKey",
                              								lv_builtInFacetKey_1_3,
                              								"gama.core.lang.Gaml.ActionFacetKey");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:6068:4: ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' )
                    {
                    // InternalGaml.g:6068:4: ( ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':' )
                    // InternalGaml.g:6069:5: ( (lv_left_2_0= ruleVariableRef ) ) otherlv_3= ':'
                    {
                    // InternalGaml.g:6069:5: ( (lv_left_2_0= ruleVariableRef ) )
                    // InternalGaml.g:6070:6: (lv_left_2_0= ruleVariableRef )
                    {
                    // InternalGaml.g:6070:6: (lv_left_2_0= ruleVariableRef )
                    // InternalGaml.g:6071:7: lv_left_2_0= ruleVariableRef
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getParameterAccess().getLeftVariableRefParserRuleCall_1_1_0_0());
                      						
                    }
                    pushFollow(FOLLOW_34);
                    lv_left_2_0=ruleVariableRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getParameterRule());
                      							}
                      							set(
                      								current,
                      								"left",
                      								lv_left_2_0,
                      								"gama.core.lang.Gaml.VariableRef");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }

                    otherlv_3=(Token)match(input,89,FOLLOW_5); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_3, grammarAccess.getParameterAccess().getColonKeyword_1_1_1());
                      				
                    }

                    }


                    }
                    break;

            }

            // InternalGaml.g:6094:3: ( (lv_right_4_0= ruleExpression ) )
            // InternalGaml.g:6095:4: (lv_right_4_0= ruleExpression )
            {
            // InternalGaml.g:6095:4: (lv_right_4_0= ruleExpression )
            // InternalGaml.g:6096:5: lv_right_4_0= ruleExpression
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getParameterAccess().getRightExpressionParserRuleCall_2_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_right_4_0=ruleExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getParameterRule());
              					}
              					set(
              						current,
              						"right",
              						lv_right_4_0,
              						"gama.core.lang.Gaml.Expression");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleUnitRef"
    // InternalGaml.g:6117:1: entryRuleUnitRef returns [EObject current=null] : iv_ruleUnitRef= ruleUnitRef EOF ;
    public final EObject entryRuleUnitRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitRef = null;


        try {
            // InternalGaml.g:6117:48: (iv_ruleUnitRef= ruleUnitRef EOF )
            // InternalGaml.g:6118:2: iv_ruleUnitRef= ruleUnitRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnitRef=ruleUnitRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnitRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnitRef"


    // $ANTLR start "ruleUnitRef"
    // InternalGaml.g:6124:1: ruleUnitRef returns [EObject current=null] : ( () ( (otherlv_1= RULE_ID ) ) ) ;
    public final EObject ruleUnitRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;


        	enterRule();

        try {
            // InternalGaml.g:6130:2: ( ( () ( (otherlv_1= RULE_ID ) ) ) )
            // InternalGaml.g:6131:2: ( () ( (otherlv_1= RULE_ID ) ) )
            {
            // InternalGaml.g:6131:2: ( () ( (otherlv_1= RULE_ID ) ) )
            // InternalGaml.g:6132:3: () ( (otherlv_1= RULE_ID ) )
            {
            // InternalGaml.g:6132:3: ()
            // InternalGaml.g:6133:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getUnitRefAccess().getUnitNameAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:6139:3: ( (otherlv_1= RULE_ID ) )
            // InternalGaml.g:6140:4: (otherlv_1= RULE_ID )
            {
            // InternalGaml.g:6140:4: (otherlv_1= RULE_ID )
            // InternalGaml.g:6141:5: otherlv_1= RULE_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getUnitRefRule());
              					}
              				
            }
            otherlv_1=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(otherlv_1, grammarAccess.getUnitRefAccess().getRefUnitFakeDefinitionCrossReference_1_0());
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitRef"


    // $ANTLR start "entryRuleVariableRef"
    // InternalGaml.g:6156:1: entryRuleVariableRef returns [EObject current=null] : iv_ruleVariableRef= ruleVariableRef EOF ;
    public final EObject entryRuleVariableRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVariableRef = null;


        try {
            // InternalGaml.g:6156:52: (iv_ruleVariableRef= ruleVariableRef EOF )
            // InternalGaml.g:6157:2: iv_ruleVariableRef= ruleVariableRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVariableRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVariableRef=ruleVariableRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVariableRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVariableRef"


    // $ANTLR start "ruleVariableRef"
    // InternalGaml.g:6163:1: ruleVariableRef returns [EObject current=null] : ( () ( ( ruleValid_ID ) ) ) ;
    public final EObject ruleVariableRef() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalGaml.g:6169:2: ( ( () ( ( ruleValid_ID ) ) ) )
            // InternalGaml.g:6170:2: ( () ( ( ruleValid_ID ) ) )
            {
            // InternalGaml.g:6170:2: ( () ( ( ruleValid_ID ) ) )
            // InternalGaml.g:6171:3: () ( ( ruleValid_ID ) )
            {
            // InternalGaml.g:6171:3: ()
            // InternalGaml.g:6172:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getVariableRefAccess().getVariableRefAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:6178:3: ( ( ruleValid_ID ) )
            // InternalGaml.g:6179:4: ( ruleValid_ID )
            {
            // InternalGaml.g:6179:4: ( ruleValid_ID )
            // InternalGaml.g:6180:5: ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getVariableRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVariableRefAccess().getRefVarDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVariableRef"


    // $ANTLR start "entryRuleTypeRef"
    // InternalGaml.g:6198:1: entryRuleTypeRef returns [EObject current=null] : iv_ruleTypeRef= ruleTypeRef EOF ;
    public final EObject entryRuleTypeRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeRef = null;


        try {
            // InternalGaml.g:6198:48: (iv_ruleTypeRef= ruleTypeRef EOF )
            // InternalGaml.g:6199:2: iv_ruleTypeRef= ruleTypeRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeRef=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeRef"


    // $ANTLR start "ruleTypeRef"
    // InternalGaml.g:6205:1: ruleTypeRef returns [EObject current=null] : ( ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) ) | ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) ) ) ;
    public final EObject ruleTypeRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_4=null;
        EObject lv_parameter_2_0 = null;

        EObject lv_parameter_5_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6211:2: ( ( ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) ) | ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) ) ) )
            // InternalGaml.g:6212:2: ( ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) ) | ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) ) )
            {
            // InternalGaml.g:6212:2: ( ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) ) | ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) ) )
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==RULE_ID) ) {
                alt111=1;
            }
            else if ( (LA111_0==38) ) {
                alt111=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }
            switch (alt111) {
                case 1 :
                    // InternalGaml.g:6213:3: ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) )
                    {
                    // InternalGaml.g:6213:3: ( () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? ) )
                    // InternalGaml.g:6214:4: () ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? )
                    {
                    // InternalGaml.g:6214:4: ()
                    // InternalGaml.g:6215:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeRefAccess().getTypeRefAction_0_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:6221:4: ( ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )? )
                    // InternalGaml.g:6222:5: ( (otherlv_1= RULE_ID ) ) ( (lv_parameter_2_0= ruleTypeInfo ) )?
                    {
                    // InternalGaml.g:6222:5: ( (otherlv_1= RULE_ID ) )
                    // InternalGaml.g:6223:6: (otherlv_1= RULE_ID )
                    {
                    // InternalGaml.g:6223:6: (otherlv_1= RULE_ID )
                    // InternalGaml.g:6224:7: otherlv_1= RULE_ID
                    {
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElement(grammarAccess.getTypeRefRule());
                      							}
                      						
                    }
                    otherlv_1=(Token)match(input,RULE_ID,FOLLOW_58); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							newLeafNode(otherlv_1, grammarAccess.getTypeRefAccess().getRefTypeDefinitionCrossReference_0_1_0_0());
                      						
                    }

                    }


                    }

                    // InternalGaml.g:6235:5: ( (lv_parameter_2_0= ruleTypeInfo ) )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==110) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // InternalGaml.g:6236:6: (lv_parameter_2_0= ruleTypeInfo )
                            {
                            // InternalGaml.g:6236:6: (lv_parameter_2_0= ruleTypeInfo )
                            // InternalGaml.g:6237:7: lv_parameter_2_0= ruleTypeInfo
                            {
                            if ( state.backtracking==0 ) {

                              							newCompositeNode(grammarAccess.getTypeRefAccess().getParameterTypeInfoParserRuleCall_0_1_1_0());
                              						
                            }
                            pushFollow(FOLLOW_2);
                            lv_parameter_2_0=ruleTypeInfo();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              							if (current==null) {
                              								current = createModelElementForParent(grammarAccess.getTypeRefRule());
                              							}
                              							set(
                              								current,
                              								"parameter",
                              								lv_parameter_2_0,
                              								"gama.core.lang.Gaml.TypeInfo");
                              							afterParserOrEnumRuleCall();
                              						
                            }

                            }


                            }
                            break;

                    }


                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:6257:3: ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) )
                    {
                    // InternalGaml.g:6257:3: ( () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) ) )
                    // InternalGaml.g:6258:4: () (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) )
                    {
                    // InternalGaml.g:6258:4: ()
                    // InternalGaml.g:6259:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTypeRefAccess().getTypeRefAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:6265:4: (otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) ) )
                    // InternalGaml.g:6266:5: otherlv_4= 'species' ( (lv_parameter_5_0= ruleTypeInfo ) )
                    {
                    otherlv_4=(Token)match(input,38,FOLLOW_59); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      					newLeafNode(otherlv_4, grammarAccess.getTypeRefAccess().getSpeciesKeyword_1_1_0());
                      				
                    }
                    // InternalGaml.g:6270:5: ( (lv_parameter_5_0= ruleTypeInfo ) )
                    // InternalGaml.g:6271:6: (lv_parameter_5_0= ruleTypeInfo )
                    {
                    // InternalGaml.g:6271:6: (lv_parameter_5_0= ruleTypeInfo )
                    // InternalGaml.g:6272:7: lv_parameter_5_0= ruleTypeInfo
                    {
                    if ( state.backtracking==0 ) {

                      							newCompositeNode(grammarAccess.getTypeRefAccess().getParameterTypeInfoParserRuleCall_1_1_1_0());
                      						
                    }
                    pushFollow(FOLLOW_2);
                    lv_parameter_5_0=ruleTypeInfo();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      							if (current==null) {
                      								current = createModelElementForParent(grammarAccess.getTypeRefRule());
                      							}
                      							set(
                      								current,
                      								"parameter",
                      								lv_parameter_5_0,
                      								"gama.core.lang.Gaml.TypeInfo");
                      							afterParserOrEnumRuleCall();
                      						
                    }

                    }


                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeRef"


    // $ANTLR start "entryRuleTypeInfo"
    // InternalGaml.g:6295:1: entryRuleTypeInfo returns [EObject current=null] : iv_ruleTypeInfo= ruleTypeInfo EOF ;
    public final EObject entryRuleTypeInfo() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeInfo = null;


        try {
            // InternalGaml.g:6295:49: (iv_ruleTypeInfo= ruleTypeInfo EOF )
            // InternalGaml.g:6296:2: iv_ruleTypeInfo= ruleTypeInfo EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeInfoRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeInfo=ruleTypeInfo();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeInfo; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeInfo"


    // $ANTLR start "ruleTypeInfo"
    // InternalGaml.g:6302:1: ruleTypeInfo returns [EObject current=null] : (otherlv_0= '<' ( (lv_first_1_0= ruleTypeRef ) ) (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )? ( ( '>' )=>otherlv_4= '>' ) ) ;
    public final EObject ruleTypeInfo() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_4=null;
        EObject lv_first_1_0 = null;

        EObject lv_second_3_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6308:2: ( (otherlv_0= '<' ( (lv_first_1_0= ruleTypeRef ) ) (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )? ( ( '>' )=>otherlv_4= '>' ) ) )
            // InternalGaml.g:6309:2: (otherlv_0= '<' ( (lv_first_1_0= ruleTypeRef ) ) (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )? ( ( '>' )=>otherlv_4= '>' ) )
            {
            // InternalGaml.g:6309:2: (otherlv_0= '<' ( (lv_first_1_0= ruleTypeRef ) ) (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )? ( ( '>' )=>otherlv_4= '>' ) )
            // InternalGaml.g:6310:3: otherlv_0= '<' ( (lv_first_1_0= ruleTypeRef ) ) (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )? ( ( '>' )=>otherlv_4= '>' )
            {
            otherlv_0=(Token)match(input,110,FOLLOW_23); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTypeInfoAccess().getLessThanSignKeyword_0());
              		
            }
            // InternalGaml.g:6314:3: ( (lv_first_1_0= ruleTypeRef ) )
            // InternalGaml.g:6315:4: (lv_first_1_0= ruleTypeRef )
            {
            // InternalGaml.g:6315:4: (lv_first_1_0= ruleTypeRef )
            // InternalGaml.g:6316:5: lv_first_1_0= ruleTypeRef
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getTypeInfoAccess().getFirstTypeRefParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_60);
            lv_first_1_0=ruleTypeRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getTypeInfoRule());
              					}
              					set(
              						current,
              						"first",
              						lv_first_1_0,
              						"gama.core.lang.Gaml.TypeRef");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }

            // InternalGaml.g:6333:3: (otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) ) )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==88) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // InternalGaml.g:6334:4: otherlv_2= ',' ( (lv_second_3_0= ruleTypeRef ) )
                    {
                    otherlv_2=(Token)match(input,88,FOLLOW_23); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      				newLeafNode(otherlv_2, grammarAccess.getTypeInfoAccess().getCommaKeyword_2_0());
                      			
                    }
                    // InternalGaml.g:6338:4: ( (lv_second_3_0= ruleTypeRef ) )
                    // InternalGaml.g:6339:5: (lv_second_3_0= ruleTypeRef )
                    {
                    // InternalGaml.g:6339:5: (lv_second_3_0= ruleTypeRef )
                    // InternalGaml.g:6340:6: lv_second_3_0= ruleTypeRef
                    {
                    if ( state.backtracking==0 ) {

                      						newCompositeNode(grammarAccess.getTypeInfoAccess().getSecondTypeRefParserRuleCall_2_1_0());
                      					
                    }
                    pushFollow(FOLLOW_30);
                    lv_second_3_0=ruleTypeRef();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElementForParent(grammarAccess.getTypeInfoRule());
                      						}
                      						set(
                      							current,
                      							"second",
                      							lv_second_3_0,
                      							"gama.core.lang.Gaml.TypeRef");
                      						afterParserOrEnumRuleCall();
                      					
                    }

                    }


                    }


                    }
                    break;

            }

            // InternalGaml.g:6358:3: ( ( '>' )=>otherlv_4= '>' )
            // InternalGaml.g:6359:4: ( '>' )=>otherlv_4= '>'
            {
            otherlv_4=(Token)match(input,83,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(otherlv_4, grammarAccess.getTypeInfoAccess().getGreaterThanSignKeyword_3());
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeInfo"


    // $ANTLR start "entryRuleActionRef"
    // InternalGaml.g:6369:1: entryRuleActionRef returns [EObject current=null] : iv_ruleActionRef= ruleActionRef EOF ;
    public final EObject entryRuleActionRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionRef = null;


        try {
            // InternalGaml.g:6369:50: (iv_ruleActionRef= ruleActionRef EOF )
            // InternalGaml.g:6370:2: iv_ruleActionRef= ruleActionRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionRef=ruleActionRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionRef"


    // $ANTLR start "ruleActionRef"
    // InternalGaml.g:6376:1: ruleActionRef returns [EObject current=null] : ( () ( ( ruleValid_ID ) ) ) ;
    public final EObject ruleActionRef() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalGaml.g:6382:2: ( ( () ( ( ruleValid_ID ) ) ) )
            // InternalGaml.g:6383:2: ( () ( ( ruleValid_ID ) ) )
            {
            // InternalGaml.g:6383:2: ( () ( ( ruleValid_ID ) ) )
            // InternalGaml.g:6384:3: () ( ( ruleValid_ID ) )
            {
            // InternalGaml.g:6384:3: ()
            // InternalGaml.g:6385:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getActionRefAccess().getActionRefAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:6391:3: ( ( ruleValid_ID ) )
            // InternalGaml.g:6392:4: ( ruleValid_ID )
            {
            // InternalGaml.g:6392:4: ( ruleValid_ID )
            // InternalGaml.g:6393:5: ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getActionRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionRefAccess().getRefActionDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionRef"


    // $ANTLR start "entryRuleEquationRef"
    // InternalGaml.g:6411:1: entryRuleEquationRef returns [EObject current=null] : iv_ruleEquationRef= ruleEquationRef EOF ;
    public final EObject entryRuleEquationRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquationRef = null;


        try {
            // InternalGaml.g:6411:52: (iv_ruleEquationRef= ruleEquationRef EOF )
            // InternalGaml.g:6412:2: iv_ruleEquationRef= ruleEquationRef EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEquationRefRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEquationRef=ruleEquationRef();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEquationRef; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquationRef"


    // $ANTLR start "ruleEquationRef"
    // InternalGaml.g:6418:1: ruleEquationRef returns [EObject current=null] : ( () ( ( ruleValid_ID ) ) ) ;
    public final EObject ruleEquationRef() throws RecognitionException {
        EObject current = null;


        	enterRule();

        try {
            // InternalGaml.g:6424:2: ( ( () ( ( ruleValid_ID ) ) ) )
            // InternalGaml.g:6425:2: ( () ( ( ruleValid_ID ) ) )
            {
            // InternalGaml.g:6425:2: ( () ( ( ruleValid_ID ) ) )
            // InternalGaml.g:6426:3: () ( ( ruleValid_ID ) )
            {
            // InternalGaml.g:6426:3: ()
            // InternalGaml.g:6427:4: 
            {
            if ( state.backtracking==0 ) {

              				current = forceCreateModelElement(
              					grammarAccess.getEquationRefAccess().getEquationRefAction_0(),
              					current);
              			
            }

            }

            // InternalGaml.g:6433:3: ( ( ruleValid_ID ) )
            // InternalGaml.g:6434:4: ( ruleValid_ID )
            {
            // InternalGaml.g:6434:4: ( ruleValid_ID )
            // InternalGaml.g:6435:5: ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getEquationRefRule());
              					}
              				
            }
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getEquationRefAccess().getRefEquationDefinitionCrossReference_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquationRef"


    // $ANTLR start "entryRuleEquationDefinition"
    // InternalGaml.g:6453:1: entryRuleEquationDefinition returns [EObject current=null] : iv_ruleEquationDefinition= ruleEquationDefinition EOF ;
    public final EObject entryRuleEquationDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquationDefinition = null;


        try {
            // InternalGaml.g:6453:59: (iv_ruleEquationDefinition= ruleEquationDefinition EOF )
            // InternalGaml.g:6454:2: iv_ruleEquationDefinition= ruleEquationDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEquationDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEquationDefinition=ruleEquationDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEquationDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquationDefinition"


    // $ANTLR start "ruleEquationDefinition"
    // InternalGaml.g:6460:1: ruleEquationDefinition returns [EObject current=null] : (this_S_Equations_0= ruleS_Equations | this_EquationFakeDefinition_1= ruleEquationFakeDefinition ) ;
    public final EObject ruleEquationDefinition() throws RecognitionException {
        EObject current = null;

        EObject this_S_Equations_0 = null;

        EObject this_EquationFakeDefinition_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:6466:2: ( (this_S_Equations_0= ruleS_Equations | this_EquationFakeDefinition_1= ruleEquationFakeDefinition ) )
            // InternalGaml.g:6467:2: (this_S_Equations_0= ruleS_Equations | this_EquationFakeDefinition_1= ruleEquationFakeDefinition )
            {
            // InternalGaml.g:6467:2: (this_S_Equations_0= ruleS_Equations | this_EquationFakeDefinition_1= ruleEquationFakeDefinition )
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==36) ) {
                alt113=1;
            }
            else if ( (LA113_0==127) ) {
                alt113=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }
            switch (alt113) {
                case 1 :
                    // InternalGaml.g:6468:3: this_S_Equations_0= ruleS_Equations
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEquationDefinitionAccess().getS_EquationsParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Equations_0=ruleS_Equations();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Equations_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:6477:3: this_EquationFakeDefinition_1= ruleEquationFakeDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getEquationDefinitionAccess().getEquationFakeDefinitionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_EquationFakeDefinition_1=ruleEquationFakeDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_EquationFakeDefinition_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquationDefinition"


    // $ANTLR start "entryRuleTypeDefinition"
    // InternalGaml.g:6489:1: entryRuleTypeDefinition returns [EObject current=null] : iv_ruleTypeDefinition= ruleTypeDefinition EOF ;
    public final EObject entryRuleTypeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeDefinition = null;


        try {
            // InternalGaml.g:6489:55: (iv_ruleTypeDefinition= ruleTypeDefinition EOF )
            // InternalGaml.g:6490:2: iv_ruleTypeDefinition= ruleTypeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeDefinition=ruleTypeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeDefinition"


    // $ANTLR start "ruleTypeDefinition"
    // InternalGaml.g:6496:1: ruleTypeDefinition returns [EObject current=null] : (this_S_Species_0= ruleS_Species | this_TypeFakeDefinition_1= ruleTypeFakeDefinition ) ;
    public final EObject ruleTypeDefinition() throws RecognitionException {
        EObject current = null;

        EObject this_S_Species_0 = null;

        EObject this_TypeFakeDefinition_1 = null;



        	enterRule();

        try {
            // InternalGaml.g:6502:2: ( (this_S_Species_0= ruleS_Species | this_TypeFakeDefinition_1= ruleTypeFakeDefinition ) )
            // InternalGaml.g:6503:2: (this_S_Species_0= ruleS_Species | this_TypeFakeDefinition_1= ruleTypeFakeDefinition )
            {
            // InternalGaml.g:6503:2: (this_S_Species_0= ruleS_Species | this_TypeFakeDefinition_1= ruleTypeFakeDefinition )
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( ((LA114_0>=38 && LA114_0<=39)) ) {
                alt114=1;
            }
            else if ( (LA114_0==123) ) {
                alt114=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }
            switch (alt114) {
                case 1 :
                    // InternalGaml.g:6504:3: this_S_Species_0= ruleS_Species
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeDefinitionAccess().getS_SpeciesParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Species_0=ruleS_Species();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Species_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:6513:3: this_TypeFakeDefinition_1= ruleTypeFakeDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTypeDefinitionAccess().getTypeFakeDefinitionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeFakeDefinition_1=ruleTypeFakeDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeFakeDefinition_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeDefinition"


    // $ANTLR start "entryRuleVarDefinition"
    // InternalGaml.g:6525:1: entryRuleVarDefinition returns [EObject current=null] : iv_ruleVarDefinition= ruleVarDefinition EOF ;
    public final EObject entryRuleVarDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarDefinition = null;


        try {
            // InternalGaml.g:6525:54: (iv_ruleVarDefinition= ruleVarDefinition EOF )
            // InternalGaml.g:6526:2: iv_ruleVarDefinition= ruleVarDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarDefinition=ruleVarDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarDefinition"


    // $ANTLR start "ruleVarDefinition"
    // InternalGaml.g:6532:1: ruleVarDefinition returns [EObject current=null] : ( ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) ) | (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment ) ) ;
    public final EObject ruleVarDefinition() throws RecognitionException {
        EObject current = null;

        EObject this_S_Definition_0 = null;

        EObject this_S_Species_1 = null;

        EObject this_S_Reflex_2 = null;

        EObject this_S_Action_3 = null;

        EObject this_S_Loop_4 = null;

        EObject this_Model_5 = null;

        EObject this_ArgumentDefinition_6 = null;

        EObject this_DefinitionFacet_7 = null;

        EObject this_VarFakeDefinition_8 = null;

        EObject this_Import_9 = null;

        EObject this_S_Experiment_10 = null;



        	enterRule();

        try {
            // InternalGaml.g:6538:2: ( ( ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) ) | (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment ) ) )
            // InternalGaml.g:6539:2: ( ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) ) | (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment ) )
            {
            // InternalGaml.g:6539:2: ( ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) ) | (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment ) )
            int alt117=2;
            alt117 = dfa117.predict(input);
            switch (alt117) {
                case 1 :
                    // InternalGaml.g:6540:3: ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) )
                    {
                    // InternalGaml.g:6540:3: ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) )
                    // InternalGaml.g:6541:4: ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop )
                    {
                    // InternalGaml.g:6553:4: (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop )
                    int alt115=5;
                    switch ( input.LA(1) ) {
                    case RULE_ID:
                        {
                        alt115=1;
                        }
                        break;
                    case 38:
                        {
                        int LA115_2 = input.LA(2);

                        if ( (LA115_2==110) ) {
                            alt115=1;
                        }
                        else if ( (LA115_2==RULE_ID) ) {
                            alt115=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return current;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 115, 2, input);

                            throw nvae;
                        }
                        }
                        break;
                    case 39:
                        {
                        alt115=2;
                        }
                        break;
                    case 79:
                    case 80:
                    case 81:
                        {
                        alt115=3;
                        }
                        break;
                    case 31:
                        {
                        alt115=4;
                        }
                        break;
                    case 23:
                        {
                        alt115=5;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 115, 0, input);

                        throw nvae;
                    }

                    switch (alt115) {
                        case 1 :
                            // InternalGaml.g:6554:5: this_S_Definition_0= ruleS_Definition
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_DefinitionParserRuleCall_0_0_0());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Definition_0=ruleS_Definition();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Definition_0;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 2 :
                            // InternalGaml.g:6563:5: this_S_Species_1= ruleS_Species
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_SpeciesParserRuleCall_0_0_1());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Species_1=ruleS_Species();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Species_1;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 3 :
                            // InternalGaml.g:6572:5: this_S_Reflex_2= ruleS_Reflex
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_ReflexParserRuleCall_0_0_2());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Reflex_2=ruleS_Reflex();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Reflex_2;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 4 :
                            // InternalGaml.g:6581:5: this_S_Action_3= ruleS_Action
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_ActionParserRuleCall_0_0_3());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Action_3=ruleS_Action();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Action_3;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;
                        case 5 :
                            // InternalGaml.g:6590:5: this_S_Loop_4= ruleS_Loop
                            {
                            if ( state.backtracking==0 ) {

                              					newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_LoopParserRuleCall_0_0_4());
                              				
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Loop_4=ruleS_Loop();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              					current = this_S_Loop_4;
                              					afterParserOrEnumRuleCall();
                              				
                            }

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalGaml.g:6601:3: (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment )
                    {
                    // InternalGaml.g:6601:3: (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment )
                    int alt116=6;
                    switch ( input.LA(1) ) {
                    case 16:
                    case 19:
                        {
                        alt116=1;
                        }
                        break;
                    case RULE_ID:
                    case 38:
                        {
                        alt116=2;
                        }
                        break;
                    case 90:
                        {
                        alt116=3;
                        }
                        break;
                    case 126:
                        {
                        alt116=4;
                        }
                        break;
                    case 17:
                        {
                        alt116=5;
                        }
                        break;
                    case 40:
                        {
                        alt116=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return current;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 116, 0, input);

                        throw nvae;
                    }

                    switch (alt116) {
                        case 1 :
                            // InternalGaml.g:6602:4: this_Model_5= ruleModel
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getModelParserRuleCall_1_0());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_Model_5=ruleModel();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_Model_5;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;
                        case 2 :
                            // InternalGaml.g:6611:4: this_ArgumentDefinition_6= ruleArgumentDefinition
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getArgumentDefinitionParserRuleCall_1_1());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_ArgumentDefinition_6=ruleArgumentDefinition();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_ArgumentDefinition_6;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;
                        case 3 :
                            // InternalGaml.g:6620:4: this_DefinitionFacet_7= ruleDefinitionFacet
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getDefinitionFacetParserRuleCall_1_2());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_DefinitionFacet_7=ruleDefinitionFacet();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_DefinitionFacet_7;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;
                        case 4 :
                            // InternalGaml.g:6629:4: this_VarFakeDefinition_8= ruleVarFakeDefinition
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getVarFakeDefinitionParserRuleCall_1_3());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_VarFakeDefinition_8=ruleVarFakeDefinition();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_VarFakeDefinition_8;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;
                        case 5 :
                            // InternalGaml.g:6638:4: this_Import_9= ruleImport
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getImportParserRuleCall_1_4());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_Import_9=ruleImport();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_Import_9;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;
                        case 6 :
                            // InternalGaml.g:6647:4: this_S_Experiment_10= ruleS_Experiment
                            {
                            if ( state.backtracking==0 ) {

                              				newCompositeNode(grammarAccess.getVarDefinitionAccess().getS_ExperimentParserRuleCall_1_5());
                              			
                            }
                            pushFollow(FOLLOW_2);
                            this_S_Experiment_10=ruleS_Experiment();

                            state._fsp--;
                            if (state.failed) return current;
                            if ( state.backtracking==0 ) {

                              				current = this_S_Experiment_10;
                              				afterParserOrEnumRuleCall();
                              			
                            }

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVarDefinition"


    // $ANTLR start "entryRuleActionDefinition"
    // InternalGaml.g:6660:1: entryRuleActionDefinition returns [EObject current=null] : iv_ruleActionDefinition= ruleActionDefinition EOF ;
    public final EObject entryRuleActionDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionDefinition = null;


        try {
            // InternalGaml.g:6660:57: (iv_ruleActionDefinition= ruleActionDefinition EOF )
            // InternalGaml.g:6661:2: iv_ruleActionDefinition= ruleActionDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionDefinition=ruleActionDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionDefinition"


    // $ANTLR start "ruleActionDefinition"
    // InternalGaml.g:6667:1: ruleActionDefinition returns [EObject current=null] : (this_S_Action_0= ruleS_Action | this_ActionFakeDefinition_1= ruleActionFakeDefinition | this_S_Definition_2= ruleS_Definition | this_TypeDefinition_3= ruleTypeDefinition ) ;
    public final EObject ruleActionDefinition() throws RecognitionException {
        EObject current = null;

        EObject this_S_Action_0 = null;

        EObject this_ActionFakeDefinition_1 = null;

        EObject this_S_Definition_2 = null;

        EObject this_TypeDefinition_3 = null;



        	enterRule();

        try {
            // InternalGaml.g:6673:2: ( (this_S_Action_0= ruleS_Action | this_ActionFakeDefinition_1= ruleActionFakeDefinition | this_S_Definition_2= ruleS_Definition | this_TypeDefinition_3= ruleTypeDefinition ) )
            // InternalGaml.g:6674:2: (this_S_Action_0= ruleS_Action | this_ActionFakeDefinition_1= ruleActionFakeDefinition | this_S_Definition_2= ruleS_Definition | this_TypeDefinition_3= ruleTypeDefinition )
            {
            // InternalGaml.g:6674:2: (this_S_Action_0= ruleS_Action | this_ActionFakeDefinition_1= ruleActionFakeDefinition | this_S_Definition_2= ruleS_Definition | this_TypeDefinition_3= ruleTypeDefinition )
            int alt118=4;
            switch ( input.LA(1) ) {
            case 31:
                {
                alt118=1;
                }
                break;
            case 124:
                {
                alt118=2;
                }
                break;
            case RULE_ID:
                {
                alt118=3;
                }
                break;
            case 38:
                {
                int LA118_4 = input.LA(2);

                if ( (LA118_4==110) ) {
                    alt118=3;
                }
                else if ( (LA118_4==RULE_ID) ) {
                    alt118=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return current;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 118, 4, input);

                    throw nvae;
                }
                }
                break;
            case 39:
            case 123:
                {
                alt118=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // InternalGaml.g:6675:3: this_S_Action_0= ruleS_Action
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getActionDefinitionAccess().getS_ActionParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Action_0=ruleS_Action();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Action_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:6684:3: this_ActionFakeDefinition_1= ruleActionFakeDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getActionDefinitionAccess().getActionFakeDefinitionParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_ActionFakeDefinition_1=ruleActionFakeDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_ActionFakeDefinition_1;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:6693:3: this_S_Definition_2= ruleS_Definition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getActionDefinitionAccess().getS_DefinitionParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_S_Definition_2=ruleS_Definition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_S_Definition_2;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:6702:3: this_TypeDefinition_3= ruleTypeDefinition
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getActionDefinitionAccess().getTypeDefinitionParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_TypeDefinition_3=ruleTypeDefinition();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_TypeDefinition_3;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionDefinition"


    // $ANTLR start "entryRuleUnitFakeDefinition"
    // InternalGaml.g:6714:1: entryRuleUnitFakeDefinition returns [EObject current=null] : iv_ruleUnitFakeDefinition= ruleUnitFakeDefinition EOF ;
    public final EObject entryRuleUnitFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleUnitFakeDefinition = null;


        try {
            // InternalGaml.g:6714:59: (iv_ruleUnitFakeDefinition= ruleUnitFakeDefinition EOF )
            // InternalGaml.g:6715:2: iv_ruleUnitFakeDefinition= ruleUnitFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getUnitFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleUnitFakeDefinition=ruleUnitFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleUnitFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleUnitFakeDefinition"


    // $ANTLR start "ruleUnitFakeDefinition"
    // InternalGaml.g:6721:1: ruleUnitFakeDefinition returns [EObject current=null] : (otherlv_0= '**unit*' ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleUnitFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            // InternalGaml.g:6727:2: ( (otherlv_0= '**unit*' ( (lv_name_1_0= RULE_ID ) ) ) )
            // InternalGaml.g:6728:2: (otherlv_0= '**unit*' ( (lv_name_1_0= RULE_ID ) ) )
            {
            // InternalGaml.g:6728:2: (otherlv_0= '**unit*' ( (lv_name_1_0= RULE_ID ) ) )
            // InternalGaml.g:6729:3: otherlv_0= '**unit*' ( (lv_name_1_0= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,122,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getUnitFakeDefinitionAccess().getUnitKeyword_0());
              		
            }
            // InternalGaml.g:6733:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGaml.g:6734:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGaml.g:6734:4: (lv_name_1_0= RULE_ID )
            // InternalGaml.g:6735:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getUnitFakeDefinitionAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getUnitFakeDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleUnitFakeDefinition"


    // $ANTLR start "entryRuleTypeFakeDefinition"
    // InternalGaml.g:6755:1: entryRuleTypeFakeDefinition returns [EObject current=null] : iv_ruleTypeFakeDefinition= ruleTypeFakeDefinition EOF ;
    public final EObject entryRuleTypeFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTypeFakeDefinition = null;


        try {
            // InternalGaml.g:6755:59: (iv_ruleTypeFakeDefinition= ruleTypeFakeDefinition EOF )
            // InternalGaml.g:6756:2: iv_ruleTypeFakeDefinition= ruleTypeFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTypeFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTypeFakeDefinition=ruleTypeFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTypeFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTypeFakeDefinition"


    // $ANTLR start "ruleTypeFakeDefinition"
    // InternalGaml.g:6762:1: ruleTypeFakeDefinition returns [EObject current=null] : (otherlv_0= '**type*' ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleTypeFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            // InternalGaml.g:6768:2: ( (otherlv_0= '**type*' ( (lv_name_1_0= RULE_ID ) ) ) )
            // InternalGaml.g:6769:2: (otherlv_0= '**type*' ( (lv_name_1_0= RULE_ID ) ) )
            {
            // InternalGaml.g:6769:2: (otherlv_0= '**type*' ( (lv_name_1_0= RULE_ID ) ) )
            // InternalGaml.g:6770:3: otherlv_0= '**type*' ( (lv_name_1_0= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,123,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getTypeFakeDefinitionAccess().getTypeKeyword_0());
              		
            }
            // InternalGaml.g:6774:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGaml.g:6775:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGaml.g:6775:4: (lv_name_1_0= RULE_ID )
            // InternalGaml.g:6776:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getTypeFakeDefinitionAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getTypeFakeDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTypeFakeDefinition"


    // $ANTLR start "entryRuleActionFakeDefinition"
    // InternalGaml.g:6796:1: entryRuleActionFakeDefinition returns [EObject current=null] : iv_ruleActionFakeDefinition= ruleActionFakeDefinition EOF ;
    public final EObject entryRuleActionFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleActionFakeDefinition = null;


        try {
            // InternalGaml.g:6796:61: (iv_ruleActionFakeDefinition= ruleActionFakeDefinition EOF )
            // InternalGaml.g:6797:2: iv_ruleActionFakeDefinition= ruleActionFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getActionFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleActionFakeDefinition=ruleActionFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleActionFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleActionFakeDefinition"


    // $ANTLR start "ruleActionFakeDefinition"
    // InternalGaml.g:6803:1: ruleActionFakeDefinition returns [EObject current=null] : (otherlv_0= '**action*' ( (lv_name_1_0= ruleValid_ID ) ) ) ;
    public final EObject ruleActionFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6809:2: ( (otherlv_0= '**action*' ( (lv_name_1_0= ruleValid_ID ) ) ) )
            // InternalGaml.g:6810:2: (otherlv_0= '**action*' ( (lv_name_1_0= ruleValid_ID ) ) )
            {
            // InternalGaml.g:6810:2: (otherlv_0= '**action*' ( (lv_name_1_0= ruleValid_ID ) ) )
            // InternalGaml.g:6811:3: otherlv_0= '**action*' ( (lv_name_1_0= ruleValid_ID ) )
            {
            otherlv_0=(Token)match(input,124,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getActionFakeDefinitionAccess().getActionKeyword_0());
              		
            }
            // InternalGaml.g:6815:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:6816:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:6816:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:6817:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getActionFakeDefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getActionFakeDefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleActionFakeDefinition"


    // $ANTLR start "entryRuleSkillFakeDefinition"
    // InternalGaml.g:6838:1: entryRuleSkillFakeDefinition returns [EObject current=null] : iv_ruleSkillFakeDefinition= ruleSkillFakeDefinition EOF ;
    public final EObject entryRuleSkillFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSkillFakeDefinition = null;


        try {
            // InternalGaml.g:6838:60: (iv_ruleSkillFakeDefinition= ruleSkillFakeDefinition EOF )
            // InternalGaml.g:6839:2: iv_ruleSkillFakeDefinition= ruleSkillFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getSkillFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleSkillFakeDefinition=ruleSkillFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleSkillFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSkillFakeDefinition"


    // $ANTLR start "ruleSkillFakeDefinition"
    // InternalGaml.g:6845:1: ruleSkillFakeDefinition returns [EObject current=null] : (otherlv_0= '**skill*' ( (lv_name_1_0= RULE_ID ) ) ) ;
    public final EObject ruleSkillFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;


        	enterRule();

        try {
            // InternalGaml.g:6851:2: ( (otherlv_0= '**skill*' ( (lv_name_1_0= RULE_ID ) ) ) )
            // InternalGaml.g:6852:2: (otherlv_0= '**skill*' ( (lv_name_1_0= RULE_ID ) ) )
            {
            // InternalGaml.g:6852:2: (otherlv_0= '**skill*' ( (lv_name_1_0= RULE_ID ) ) )
            // InternalGaml.g:6853:3: otherlv_0= '**skill*' ( (lv_name_1_0= RULE_ID ) )
            {
            otherlv_0=(Token)match(input,125,FOLLOW_7); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getSkillFakeDefinitionAccess().getSkillKeyword_0());
              		
            }
            // InternalGaml.g:6857:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalGaml.g:6858:4: (lv_name_1_0= RULE_ID )
            {
            // InternalGaml.g:6858:4: (lv_name_1_0= RULE_ID )
            // InternalGaml.g:6859:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					newLeafNode(lv_name_1_0, grammarAccess.getSkillFakeDefinitionAccess().getNameIDTerminalRuleCall_1_0());
              				
            }
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElement(grammarAccess.getSkillFakeDefinitionRule());
              					}
              					setWithLastConsumed(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.ID");
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSkillFakeDefinition"


    // $ANTLR start "entryRuleVarFakeDefinition"
    // InternalGaml.g:6879:1: entryRuleVarFakeDefinition returns [EObject current=null] : iv_ruleVarFakeDefinition= ruleVarFakeDefinition EOF ;
    public final EObject entryRuleVarFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVarFakeDefinition = null;


        try {
            // InternalGaml.g:6879:58: (iv_ruleVarFakeDefinition= ruleVarFakeDefinition EOF )
            // InternalGaml.g:6880:2: iv_ruleVarFakeDefinition= ruleVarFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getVarFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleVarFakeDefinition=ruleVarFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleVarFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVarFakeDefinition"


    // $ANTLR start "ruleVarFakeDefinition"
    // InternalGaml.g:6886:1: ruleVarFakeDefinition returns [EObject current=null] : (otherlv_0= '**var*' ( (lv_name_1_0= ruleValid_ID ) ) ) ;
    public final EObject ruleVarFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6892:2: ( (otherlv_0= '**var*' ( (lv_name_1_0= ruleValid_ID ) ) ) )
            // InternalGaml.g:6893:2: (otherlv_0= '**var*' ( (lv_name_1_0= ruleValid_ID ) ) )
            {
            // InternalGaml.g:6893:2: (otherlv_0= '**var*' ( (lv_name_1_0= ruleValid_ID ) ) )
            // InternalGaml.g:6894:3: otherlv_0= '**var*' ( (lv_name_1_0= ruleValid_ID ) )
            {
            otherlv_0=(Token)match(input,126,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getVarFakeDefinitionAccess().getVarKeyword_0());
              		
            }
            // InternalGaml.g:6898:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:6899:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:6899:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:6900:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getVarFakeDefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getVarFakeDefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVarFakeDefinition"


    // $ANTLR start "entryRuleEquationFakeDefinition"
    // InternalGaml.g:6921:1: entryRuleEquationFakeDefinition returns [EObject current=null] : iv_ruleEquationFakeDefinition= ruleEquationFakeDefinition EOF ;
    public final EObject entryRuleEquationFakeDefinition() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEquationFakeDefinition = null;


        try {
            // InternalGaml.g:6921:63: (iv_ruleEquationFakeDefinition= ruleEquationFakeDefinition EOF )
            // InternalGaml.g:6922:2: iv_ruleEquationFakeDefinition= ruleEquationFakeDefinition EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getEquationFakeDefinitionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleEquationFakeDefinition=ruleEquationFakeDefinition();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleEquationFakeDefinition; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEquationFakeDefinition"


    // $ANTLR start "ruleEquationFakeDefinition"
    // InternalGaml.g:6928:1: ruleEquationFakeDefinition returns [EObject current=null] : (otherlv_0= '**equation*' ( (lv_name_1_0= ruleValid_ID ) ) ) ;
    public final EObject ruleEquationFakeDefinition() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:6934:2: ( (otherlv_0= '**equation*' ( (lv_name_1_0= ruleValid_ID ) ) ) )
            // InternalGaml.g:6935:2: (otherlv_0= '**equation*' ( (lv_name_1_0= ruleValid_ID ) ) )
            {
            // InternalGaml.g:6935:2: (otherlv_0= '**equation*' ( (lv_name_1_0= ruleValid_ID ) ) )
            // InternalGaml.g:6936:3: otherlv_0= '**equation*' ( (lv_name_1_0= ruleValid_ID ) )
            {
            otherlv_0=(Token)match(input,127,FOLLOW_12); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              			newLeafNode(otherlv_0, grammarAccess.getEquationFakeDefinitionAccess().getEquationKeyword_0());
              		
            }
            // InternalGaml.g:6940:3: ( (lv_name_1_0= ruleValid_ID ) )
            // InternalGaml.g:6941:4: (lv_name_1_0= ruleValid_ID )
            {
            // InternalGaml.g:6941:4: (lv_name_1_0= ruleValid_ID )
            // InternalGaml.g:6942:5: lv_name_1_0= ruleValid_ID
            {
            if ( state.backtracking==0 ) {

              					newCompositeNode(grammarAccess.getEquationFakeDefinitionAccess().getNameValid_IDParserRuleCall_1_0());
              				
            }
            pushFollow(FOLLOW_2);
            lv_name_1_0=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {

              					if (current==null) {
              						current = createModelElementForParent(grammarAccess.getEquationFakeDefinitionRule());
              					}
              					set(
              						current,
              						"name",
              						lv_name_1_0,
              						"gama.core.lang.Gaml.Valid_ID");
              					afterParserOrEnumRuleCall();
              				
            }

            }


            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEquationFakeDefinition"


    // $ANTLR start "entryRuleValid_ID"
    // InternalGaml.g:6963:1: entryRuleValid_ID returns [String current=null] : iv_ruleValid_ID= ruleValid_ID EOF ;
    public final String entryRuleValid_ID() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleValid_ID = null;


        try {
            // InternalGaml.g:6963:48: (iv_ruleValid_ID= ruleValid_ID EOF )
            // InternalGaml.g:6964:2: iv_ruleValid_ID= ruleValid_ID EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getValid_IDRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleValid_ID=ruleValid_ID();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleValid_ID.getText(); 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleValid_ID"


    // $ANTLR start "ruleValid_ID"
    // InternalGaml.g:6970:1: ruleValid_ID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this__SpeciesKey_0= rule_SpeciesKey | this__DoKey_1= rule_DoKey | this__ReflexKey_2= rule_ReflexKey | this__1Expr_Facets_BlockOrEnd_Key_3= rule_1Expr_Facets_BlockOrEnd_Key | this__EquationsKey_4= rule_EquationsKey | this_ID_5= RULE_ID | this__ExperimentKey_6= rule_ExperimentKey ) ;
    public final AntlrDatatypeRuleToken ruleValid_ID() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_5=null;
        AntlrDatatypeRuleToken this__SpeciesKey_0 = null;

        AntlrDatatypeRuleToken this__DoKey_1 = null;

        AntlrDatatypeRuleToken this__ReflexKey_2 = null;

        AntlrDatatypeRuleToken this__1Expr_Facets_BlockOrEnd_Key_3 = null;

        AntlrDatatypeRuleToken this__EquationsKey_4 = null;

        AntlrDatatypeRuleToken this__ExperimentKey_6 = null;



        	enterRule();

        try {
            // InternalGaml.g:6976:2: ( (this__SpeciesKey_0= rule_SpeciesKey | this__DoKey_1= rule_DoKey | this__ReflexKey_2= rule_ReflexKey | this__1Expr_Facets_BlockOrEnd_Key_3= rule_1Expr_Facets_BlockOrEnd_Key | this__EquationsKey_4= rule_EquationsKey | this_ID_5= RULE_ID | this__ExperimentKey_6= rule_ExperimentKey ) )
            // InternalGaml.g:6977:2: (this__SpeciesKey_0= rule_SpeciesKey | this__DoKey_1= rule_DoKey | this__ReflexKey_2= rule_ReflexKey | this__1Expr_Facets_BlockOrEnd_Key_3= rule_1Expr_Facets_BlockOrEnd_Key | this__EquationsKey_4= rule_EquationsKey | this_ID_5= RULE_ID | this__ExperimentKey_6= rule_ExperimentKey )
            {
            // InternalGaml.g:6977:2: (this__SpeciesKey_0= rule_SpeciesKey | this__DoKey_1= rule_DoKey | this__ReflexKey_2= rule_ReflexKey | this__1Expr_Facets_BlockOrEnd_Key_3= rule_1Expr_Facets_BlockOrEnd_Key | this__EquationsKey_4= rule_EquationsKey | this_ID_5= RULE_ID | this__ExperimentKey_6= rule_ExperimentKey )
            int alt119=7;
            switch ( input.LA(1) ) {
            case 38:
            case 39:
                {
                alt119=1;
                }
                break;
            case 77:
            case 78:
                {
                alt119=2;
                }
                break;
            case 79:
            case 80:
            case 81:
                {
                alt119=3;
                }
                break;
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
                {
                alt119=4;
                }
                break;
            case 36:
                {
                alt119=5;
                }
                break;
            case RULE_ID:
                {
                alt119=6;
                }
                break;
            case 40:
                {
                alt119=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }

            switch (alt119) {
                case 1 :
                    // InternalGaml.g:6978:3: this__SpeciesKey_0= rule_SpeciesKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_SpeciesKeyParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__SpeciesKey_0=rule_SpeciesKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__SpeciesKey_0);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:6989:3: this__DoKey_1= rule_DoKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_DoKeyParserRuleCall_1());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__DoKey_1=rule_DoKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__DoKey_1);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 3 :
                    // InternalGaml.g:7000:3: this__ReflexKey_2= rule_ReflexKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_ReflexKeyParserRuleCall_2());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__ReflexKey_2=rule_ReflexKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__ReflexKey_2);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 4 :
                    // InternalGaml.g:7011:3: this__1Expr_Facets_BlockOrEnd_Key_3= rule_1Expr_Facets_BlockOrEnd_Key
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_1Expr_Facets_BlockOrEnd_KeyParserRuleCall_3());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__1Expr_Facets_BlockOrEnd_Key_3=rule_1Expr_Facets_BlockOrEnd_Key();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__1Expr_Facets_BlockOrEnd_Key_3);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 5 :
                    // InternalGaml.g:7022:3: this__EquationsKey_4= rule_EquationsKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_EquationsKeyParserRuleCall_4());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__EquationsKey_4=rule_EquationsKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__EquationsKey_4);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 6 :
                    // InternalGaml.g:7033:3: this_ID_5= RULE_ID
                    {
                    this_ID_5=(Token)match(input,RULE_ID,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this_ID_5);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			newLeafNode(this_ID_5, grammarAccess.getValid_IDAccess().getIDTerminalRuleCall_5());
                      		
                    }

                    }
                    break;
                case 7 :
                    // InternalGaml.g:7041:3: this__ExperimentKey_6= rule_ExperimentKey
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getValid_IDAccess().get_ExperimentKeyParserRuleCall_6());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this__ExperimentKey_6=rule_ExperimentKey();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current.merge(this__ExperimentKey_6);
                      		
                    }
                    if ( state.backtracking==0 ) {

                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleValid_ID"


    // $ANTLR start "entryRuleTerminalExpression"
    // InternalGaml.g:7055:1: entryRuleTerminalExpression returns [EObject current=null] : iv_ruleTerminalExpression= ruleTerminalExpression EOF ;
    public final EObject entryRuleTerminalExpression() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTerminalExpression = null;


        try {
            // InternalGaml.g:7055:59: (iv_ruleTerminalExpression= ruleTerminalExpression EOF )
            // InternalGaml.g:7056:2: iv_ruleTerminalExpression= ruleTerminalExpression EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getTerminalExpressionRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleTerminalExpression=ruleTerminalExpression();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleTerminalExpression; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTerminalExpression"


    // $ANTLR start "ruleTerminalExpression"
    // InternalGaml.g:7062:1: ruleTerminalExpression returns [EObject current=null] : (this_StringLiteral_0= ruleStringLiteral | ( () ( (lv_op_2_0= RULE_INTEGER ) ) ) | ( () ( (lv_op_4_0= RULE_DOUBLE ) ) ) | ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) ) | ( () ( (lv_op_8_0= RULE_KEYWORD ) ) ) ) ;
    public final EObject ruleTerminalExpression() throws RecognitionException {
        EObject current = null;

        Token lv_op_2_0=null;
        Token lv_op_4_0=null;
        Token lv_op_6_0=null;
        Token lv_op_8_0=null;
        EObject this_StringLiteral_0 = null;



        	enterRule();

        try {
            // InternalGaml.g:7068:2: ( (this_StringLiteral_0= ruleStringLiteral | ( () ( (lv_op_2_0= RULE_INTEGER ) ) ) | ( () ( (lv_op_4_0= RULE_DOUBLE ) ) ) | ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) ) | ( () ( (lv_op_8_0= RULE_KEYWORD ) ) ) ) )
            // InternalGaml.g:7069:2: (this_StringLiteral_0= ruleStringLiteral | ( () ( (lv_op_2_0= RULE_INTEGER ) ) ) | ( () ( (lv_op_4_0= RULE_DOUBLE ) ) ) | ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) ) | ( () ( (lv_op_8_0= RULE_KEYWORD ) ) ) )
            {
            // InternalGaml.g:7069:2: (this_StringLiteral_0= ruleStringLiteral | ( () ( (lv_op_2_0= RULE_INTEGER ) ) ) | ( () ( (lv_op_4_0= RULE_DOUBLE ) ) ) | ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) ) | ( () ( (lv_op_8_0= RULE_KEYWORD ) ) ) )
            int alt120=5;
            switch ( input.LA(1) ) {
            case RULE_STRING:
                {
                alt120=1;
                }
                break;
            case RULE_INTEGER:
                {
                alt120=2;
                }
                break;
            case RULE_DOUBLE:
                {
                alt120=3;
                }
                break;
            case RULE_BOOLEAN:
                {
                alt120=4;
                }
                break;
            case RULE_KEYWORD:
                {
                alt120=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return current;}
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }

            switch (alt120) {
                case 1 :
                    // InternalGaml.g:7070:3: this_StringLiteral_0= ruleStringLiteral
                    {
                    if ( state.backtracking==0 ) {

                      			newCompositeNode(grammarAccess.getTerminalExpressionAccess().getStringLiteralParserRuleCall_0());
                      		
                    }
                    pushFollow(FOLLOW_2);
                    this_StringLiteral_0=ruleStringLiteral();

                    state._fsp--;
                    if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      			current = this_StringLiteral_0;
                      			afterParserOrEnumRuleCall();
                      		
                    }

                    }
                    break;
                case 2 :
                    // InternalGaml.g:7079:3: ( () ( (lv_op_2_0= RULE_INTEGER ) ) )
                    {
                    // InternalGaml.g:7079:3: ( () ( (lv_op_2_0= RULE_INTEGER ) ) )
                    // InternalGaml.g:7080:4: () ( (lv_op_2_0= RULE_INTEGER ) )
                    {
                    // InternalGaml.g:7080:4: ()
                    // InternalGaml.g:7081:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getIntLiteralAction_1_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:7087:4: ( (lv_op_2_0= RULE_INTEGER ) )
                    // InternalGaml.g:7088:5: (lv_op_2_0= RULE_INTEGER )
                    {
                    // InternalGaml.g:7088:5: (lv_op_2_0= RULE_INTEGER )
                    // InternalGaml.g:7089:6: lv_op_2_0= RULE_INTEGER
                    {
                    lv_op_2_0=(Token)match(input,RULE_INTEGER,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_2_0, grammarAccess.getTerminalExpressionAccess().getOpINTEGERTerminalRuleCall_1_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_2_0,
                      							"gama.core.lang.Gaml.INTEGER");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalGaml.g:7107:3: ( () ( (lv_op_4_0= RULE_DOUBLE ) ) )
                    {
                    // InternalGaml.g:7107:3: ( () ( (lv_op_4_0= RULE_DOUBLE ) ) )
                    // InternalGaml.g:7108:4: () ( (lv_op_4_0= RULE_DOUBLE ) )
                    {
                    // InternalGaml.g:7108:4: ()
                    // InternalGaml.g:7109:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getDoubleLiteralAction_2_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:7115:4: ( (lv_op_4_0= RULE_DOUBLE ) )
                    // InternalGaml.g:7116:5: (lv_op_4_0= RULE_DOUBLE )
                    {
                    // InternalGaml.g:7116:5: (lv_op_4_0= RULE_DOUBLE )
                    // InternalGaml.g:7117:6: lv_op_4_0= RULE_DOUBLE
                    {
                    lv_op_4_0=(Token)match(input,RULE_DOUBLE,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_4_0, grammarAccess.getTerminalExpressionAccess().getOpDOUBLETerminalRuleCall_2_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_4_0,
                      							"gama.core.lang.Gaml.DOUBLE");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalGaml.g:7135:3: ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) )
                    {
                    // InternalGaml.g:7135:3: ( () ( (lv_op_6_0= RULE_BOOLEAN ) ) )
                    // InternalGaml.g:7136:4: () ( (lv_op_6_0= RULE_BOOLEAN ) )
                    {
                    // InternalGaml.g:7136:4: ()
                    // InternalGaml.g:7137:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getBooleanLiteralAction_3_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:7143:4: ( (lv_op_6_0= RULE_BOOLEAN ) )
                    // InternalGaml.g:7144:5: (lv_op_6_0= RULE_BOOLEAN )
                    {
                    // InternalGaml.g:7144:5: (lv_op_6_0= RULE_BOOLEAN )
                    // InternalGaml.g:7145:6: lv_op_6_0= RULE_BOOLEAN
                    {
                    lv_op_6_0=(Token)match(input,RULE_BOOLEAN,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_6_0, grammarAccess.getTerminalExpressionAccess().getOpBOOLEANTerminalRuleCall_3_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_6_0,
                      							"gama.core.lang.Gaml.BOOLEAN");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalGaml.g:7163:3: ( () ( (lv_op_8_0= RULE_KEYWORD ) ) )
                    {
                    // InternalGaml.g:7163:3: ( () ( (lv_op_8_0= RULE_KEYWORD ) ) )
                    // InternalGaml.g:7164:4: () ( (lv_op_8_0= RULE_KEYWORD ) )
                    {
                    // InternalGaml.g:7164:4: ()
                    // InternalGaml.g:7165:5: 
                    {
                    if ( state.backtracking==0 ) {

                      					current = forceCreateModelElement(
                      						grammarAccess.getTerminalExpressionAccess().getReservedLiteralAction_4_0(),
                      						current);
                      				
                    }

                    }

                    // InternalGaml.g:7171:4: ( (lv_op_8_0= RULE_KEYWORD ) )
                    // InternalGaml.g:7172:5: (lv_op_8_0= RULE_KEYWORD )
                    {
                    // InternalGaml.g:7172:5: (lv_op_8_0= RULE_KEYWORD )
                    // InternalGaml.g:7173:6: lv_op_8_0= RULE_KEYWORD
                    {
                    lv_op_8_0=(Token)match(input,RULE_KEYWORD,FOLLOW_2); if (state.failed) return current;
                    if ( state.backtracking==0 ) {

                      						newLeafNode(lv_op_8_0, grammarAccess.getTerminalExpressionAccess().getOpKEYWORDTerminalRuleCall_4_1_0());
                      					
                    }
                    if ( state.backtracking==0 ) {

                      						if (current==null) {
                      							current = createModelElement(grammarAccess.getTerminalExpressionRule());
                      						}
                      						setWithLastConsumed(
                      							current,
                      							"op",
                      							lv_op_8_0,
                      							"gama.core.lang.Gaml.KEYWORD");
                      					
                    }

                    }


                    }


                    }


                    }
                    break;

            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTerminalExpression"


    // $ANTLR start "entryRuleStringLiteral"
    // InternalGaml.g:7194:1: entryRuleStringLiteral returns [EObject current=null] : iv_ruleStringLiteral= ruleStringLiteral EOF ;
    public final EObject entryRuleStringLiteral() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStringLiteral = null;


        try {
            // InternalGaml.g:7194:54: (iv_ruleStringLiteral= ruleStringLiteral EOF )
            // InternalGaml.g:7195:2: iv_ruleStringLiteral= ruleStringLiteral EOF
            {
            if ( state.backtracking==0 ) {
               newCompositeNode(grammarAccess.getStringLiteralRule()); 
            }
            pushFollow(FOLLOW_1);
            iv_ruleStringLiteral=ruleStringLiteral();

            state._fsp--;
            if (state.failed) return current;
            if ( state.backtracking==0 ) {
               current =iv_ruleStringLiteral; 
            }
            match(input,EOF,FOLLOW_2); if (state.failed) return current;

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringLiteral"


    // $ANTLR start "ruleStringLiteral"
    // InternalGaml.g:7201:1: ruleStringLiteral returns [EObject current=null] : ( (lv_op_0_0= RULE_STRING ) ) ;
    public final EObject ruleStringLiteral() throws RecognitionException {
        EObject current = null;

        Token lv_op_0_0=null;


        	enterRule();

        try {
            // InternalGaml.g:7207:2: ( ( (lv_op_0_0= RULE_STRING ) ) )
            // InternalGaml.g:7208:2: ( (lv_op_0_0= RULE_STRING ) )
            {
            // InternalGaml.g:7208:2: ( (lv_op_0_0= RULE_STRING ) )
            // InternalGaml.g:7209:3: (lv_op_0_0= RULE_STRING )
            {
            // InternalGaml.g:7209:3: (lv_op_0_0= RULE_STRING )
            // InternalGaml.g:7210:4: lv_op_0_0= RULE_STRING
            {
            lv_op_0_0=(Token)match(input,RULE_STRING,FOLLOW_2); if (state.failed) return current;
            if ( state.backtracking==0 ) {

              				newLeafNode(lv_op_0_0, grammarAccess.getStringLiteralAccess().getOpSTRINGTerminalRuleCall_0());
              			
            }
            if ( state.backtracking==0 ) {

              				if (current==null) {
              					current = createModelElement(grammarAccess.getStringLiteralRule());
              				}
              				setWithLastConsumed(
              					current,
              					"op",
              					lv_op_0_0,
              					"gama.core.lang.Gaml.STRING");
              			
            }

            }


            }


            }

            if ( state.backtracking==0 ) {

              	leaveRule();

            }
        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringLiteral"

    // $ANTLR start synpred1_InternalGaml
    public final void synpred1_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:80:4: ( '@' | 'model' )
        // InternalGaml.g:
        {
        if ( input.LA(1)==16||input.LA(1)==19 ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }
    }
    // $ANTLR end synpred1_InternalGaml

    // $ANTLR start synpred2_InternalGaml
    public final void synpred2_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:1012:5: ( ruleS_Declaration )
        // InternalGaml.g:1012:6: ruleS_Declaration
        {
        pushFollow(FOLLOW_2);
        ruleS_Declaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_InternalGaml

    // $ANTLR start synpred3_InternalGaml
    public final void synpred3_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:1025:6: ( ruleS_Assignment )
        // InternalGaml.g:1025:7: ruleS_Assignment
        {
        pushFollow(FOLLOW_2);
        ruleS_Assignment();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_InternalGaml

    // $ANTLR start synpred4_InternalGaml
    public final void synpred4_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:1485:5: ( 'else' )
        // InternalGaml.g:1485:6: 'else'
        {
        match(input,25,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_InternalGaml

    // $ANTLR start synpred5_InternalGaml
    public final void synpred5_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:1583:5: ( 'catch' )
        // InternalGaml.g:1583:6: 'catch'
        {
        match(input,27,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_InternalGaml

    // $ANTLR start synpred6_InternalGaml
    public final void synpred6_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:1767:4: ( 'species' | RULE_ID )
        // InternalGaml.g:
        {
        if ( input.LA(1)==RULE_ID||input.LA(1)==38 ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }
    }
    // $ANTLR end synpred6_InternalGaml

    // $ANTLR start synpred8_InternalGaml
    public final void synpred8_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:4182:4: ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )
        // InternalGaml.g:4182:5: ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) )
        {
        // InternalGaml.g:4182:5: ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) )
        int alt122=2;
        int LA122_0 = input.LA(1);

        if ( (LA122_0==RULE_ID||LA122_0==36||(LA122_0>=38 && LA122_0<=81)) ) {
            alt122=1;
        }
        else if ( ((LA122_0>=90 && LA122_0<=101)) ) {
            alt122=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 122, 0, input);

            throw nvae;
        }
        switch (alt122) {
            case 1 :
                // InternalGaml.g:4183:5: ( ( ( ruleValid_ID ) ) '::' )
                {
                // InternalGaml.g:4183:5: ( ( ( ruleValid_ID ) ) '::' )
                // InternalGaml.g:4184:6: ( ( ruleValid_ID ) ) '::'
                {
                // InternalGaml.g:4184:6: ( ( ruleValid_ID ) )
                // InternalGaml.g:4185:7: ( ruleValid_ID )
                {
                // InternalGaml.g:4185:7: ( ruleValid_ID )
                // InternalGaml.g:4186:8: ruleValid_ID
                {
                pushFollow(FOLLOW_37);
                ruleValid_ID();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                match(input,103,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;
            case 2 :
                // InternalGaml.g:4192:5: ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' )
                {
                // InternalGaml.g:4192:5: ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' )
                // InternalGaml.g:4193:6: ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':'
                {
                // InternalGaml.g:4193:6: ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) )
                // InternalGaml.g:4194:7: ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) )
                {
                // InternalGaml.g:4194:7: ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) )
                // InternalGaml.g:4195:8: ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey )
                {
                // InternalGaml.g:4195:8: ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey )
                int alt121=3;
                switch ( input.LA(1) ) {
                case 90:
                    {
                    alt121=1;
                    }
                    break;
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                    {
                    alt121=2;
                    }
                    break;
                case 100:
                case 101:
                    {
                    alt121=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 121, 0, input);

                    throw nvae;
                }

                switch (alt121) {
                    case 1 :
                        // InternalGaml.g:4196:9: ruleDefinitionFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleDefinitionFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 2 :
                        // InternalGaml.g:4197:14: ruleSpecialFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleSpecialFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 3 :
                        // InternalGaml.g:4198:14: ruleActionFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleActionFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;

                }


                }


                }

                match(input,89,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;

        }


        }
    }
    // $ANTLR end synpred8_InternalGaml

    // $ANTLR start synpred9_InternalGaml
    public final void synpred9_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:4244:4: ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )
        // InternalGaml.g:4244:5: ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) )
        {
        // InternalGaml.g:4244:5: ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) )
        int alt124=2;
        int LA124_0 = input.LA(1);

        if ( (LA124_0==RULE_ID||LA124_0==36||(LA124_0>=38 && LA124_0<=81)) ) {
            alt124=1;
        }
        else if ( ((LA124_0>=90 && LA124_0<=101)) ) {
            alt124=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 124, 0, input);

            throw nvae;
        }
        switch (alt124) {
            case 1 :
                // InternalGaml.g:4245:5: ( ( ( ruleValid_ID ) ) '::' )
                {
                // InternalGaml.g:4245:5: ( ( ( ruleValid_ID ) ) '::' )
                // InternalGaml.g:4246:6: ( ( ruleValid_ID ) ) '::'
                {
                // InternalGaml.g:4246:6: ( ( ruleValid_ID ) )
                // InternalGaml.g:4247:7: ( ruleValid_ID )
                {
                // InternalGaml.g:4247:7: ( ruleValid_ID )
                // InternalGaml.g:4248:8: ruleValid_ID
                {
                pushFollow(FOLLOW_37);
                ruleValid_ID();

                state._fsp--;
                if (state.failed) return ;

                }


                }

                match(input,103,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;
            case 2 :
                // InternalGaml.g:4254:5: ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' )
                {
                // InternalGaml.g:4254:5: ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' )
                // InternalGaml.g:4255:6: ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':'
                {
                // InternalGaml.g:4255:6: ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) )
                // InternalGaml.g:4256:7: ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) )
                {
                // InternalGaml.g:4256:7: ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) )
                // InternalGaml.g:4257:8: ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey )
                {
                // InternalGaml.g:4257:8: ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey )
                int alt123=3;
                switch ( input.LA(1) ) {
                case 90:
                    {
                    alt123=1;
                    }
                    break;
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                case 98:
                case 99:
                    {
                    alt123=2;
                    }
                    break;
                case 100:
                case 101:
                    {
                    alt123=3;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 123, 0, input);

                    throw nvae;
                }

                switch (alt123) {
                    case 1 :
                        // InternalGaml.g:4258:9: ruleDefinitionFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleDefinitionFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 2 :
                        // InternalGaml.g:4259:14: ruleSpecialFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleSpecialFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;
                    case 3 :
                        // InternalGaml.g:4260:14: ruleActionFacetKey
                        {
                        pushFollow(FOLLOW_34);
                        ruleActionFacetKey();

                        state._fsp--;
                        if (state.failed) return ;

                        }
                        break;

                }


                }


                }

                match(input,89,FOLLOW_2); if (state.failed) return ;

                }


                }
                break;

        }


        }
    }
    // $ANTLR end synpred9_InternalGaml

    // $ANTLR start synpred10_InternalGaml
    public final void synpred10_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:5765:4: ( ruleFunction )
        // InternalGaml.g:5765:5: ruleFunction
        {
        pushFollow(FOLLOW_2);
        ruleFunction();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_InternalGaml

    // $ANTLR start synpred11_InternalGaml
    public final void synpred11_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:6359:4: ( '>' )
        // InternalGaml.g:6359:5: '>'
        {
        match(input,83,FOLLOW_2); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_InternalGaml

    // $ANTLR start synpred12_InternalGaml
    public final void synpred12_InternalGaml_fragment() throws RecognitionException {   
        // InternalGaml.g:6541:4: ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )
        // InternalGaml.g:6541:5: ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop )
        {
        // InternalGaml.g:6541:5: ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop )
        int alt125=5;
        switch ( input.LA(1) ) {
        case RULE_ID:
            {
            alt125=1;
            }
            break;
        case 38:
            {
            int LA125_2 = input.LA(2);

            if ( (LA125_2==RULE_ID) ) {
                alt125=2;
            }
            else if ( (LA125_2==110) ) {
                alt125=1;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 2, input);

                throw nvae;
            }
            }
            break;
        case 39:
            {
            alt125=2;
            }
            break;
        case 79:
        case 80:
        case 81:
            {
            alt125=3;
            }
            break;
        case 31:
            {
            alt125=4;
            }
            break;
        case 23:
            {
            alt125=5;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 125, 0, input);

            throw nvae;
        }

        switch (alt125) {
            case 1 :
                // InternalGaml.g:6542:5: ruleS_Definition
                {
                pushFollow(FOLLOW_2);
                ruleS_Definition();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // InternalGaml.g:6544:5: ruleS_Species
                {
                pushFollow(FOLLOW_2);
                ruleS_Species();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 3 :
                // InternalGaml.g:6546:5: ruleS_Reflex
                {
                pushFollow(FOLLOW_2);
                ruleS_Reflex();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 4 :
                // InternalGaml.g:6548:5: ruleS_Action
                {
                pushFollow(FOLLOW_2);
                ruleS_Action();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 5 :
                // InternalGaml.g:6550:5: ruleS_Loop
                {
                pushFollow(FOLLOW_2);
                ruleS_Loop();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred12_InternalGaml

    // Delegated rules

    public final boolean synpred12_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_InternalGaml() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_InternalGaml_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA19 dfa19 = new DFA19(this);
    protected DFA18 dfa18 = new DFA18(this);
    protected DFA33 dfa33 = new DFA33(this);
    protected DFA49 dfa49 = new DFA49(this);
    protected DFA63 dfa63 = new DFA63(this);
    protected DFA73 dfa73 = new DFA73(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA102 dfa102 = new DFA102(this);
    protected DFA107 dfa107 = new DFA107(this);
    protected DFA117 dfa117 = new DFA117(this);
    static final String dfa_1s = "\115\uffff";
    static final String dfa_2s = "\1\4\6\0\106\uffff";
    static final String dfa_3s = "\1\167\6\0\106\uffff";
    static final String dfa_4s = "\7\uffff\2\1\1\2\103\uffff";
    static final String dfa_5s = "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\106\uffff}>";
    static final String[] dfa_6s = {
            "\1\1\5\11\15\uffff\1\10\1\11\1\uffff\1\11\1\uffff\2\11\1\uffff\1\7\1\11\3\uffff\2\11\1\2\1\3\47\11\1\4\1\5\1\6\10\uffff\14\11\12\uffff\1\11\3\uffff\4\11",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
    static final char[] dfa_2 = DFA.unpackEncodedStringToUnsignedChars(dfa_2s);
    static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
    static final short[] dfa_4 = DFA.unpackEncodedString(dfa_4s);
    static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
    static final short[][] dfa_6 = unpackEncodedStringArray(dfa_6s);

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = dfa_1;
            this.eof = dfa_1;
            this.min = dfa_2;
            this.max = dfa_3;
            this.accept = dfa_4;
            this.special = dfa_5;
            this.transition = dfa_6;
        }
        public String getDescription() {
            return "1010:3: ( ( ( ruleS_Declaration )=>this_S_Declaration_0= ruleS_Declaration ) | ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_0 = input.LA(1);

                         
                        int index19_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA19_0==RULE_ID) ) {s = 1;}

                        else if ( (LA19_0==38) ) {s = 2;}

                        else if ( (LA19_0==39) ) {s = 3;}

                        else if ( (LA19_0==79) ) {s = 4;}

                        else if ( (LA19_0==80) ) {s = 5;}

                        else if ( (LA19_0==81) ) {s = 6;}

                        else if ( (LA19_0==31) && (synpred2_InternalGaml())) {s = 7;}

                        else if ( (LA19_0==23) && (synpred2_InternalGaml())) {s = 8;}

                        else if ( ((LA19_0>=RULE_STRING && LA19_0<=RULE_KEYWORD)||LA19_0==24||LA19_0==26||(LA19_0>=28 && LA19_0<=29)||LA19_0==32||(LA19_0>=36 && LA19_0<=37)||(LA19_0>=40 && LA19_0<=78)||(LA19_0>=90 && LA19_0<=101)||LA19_0==112||(LA19_0>=116 && LA19_0<=119)) ) {s = 9;}

                         
                        input.seek(index19_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA19_1 = input.LA(1);

                         
                        int index19_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA19_2 = input.LA(1);

                         
                        int index19_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA19_3 = input.LA(1);

                         
                        int index19_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA19_4 = input.LA(1);

                         
                        int index19_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA19_5 = input.LA(1);

                         
                        int index19_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA19_6 = input.LA(1);

                         
                        int index19_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index19_6);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_7s = "\117\uffff";
    static final String dfa_8s = "\1\4\2\uffff\2\0\3\uffff\46\0\41\uffff";
    static final String dfa_9s = "\1\167\2\uffff\2\0\3\uffff\46\0\41\uffff";
    static final String dfa_10s = "\1\uffff\2\1\2\uffff\3\1\46\uffff\31\1\1\5\1\6\1\7\1\10\1\4\1\2\1\11\1\3";
    static final String dfa_11s = "\1\0\2\uffff\1\1\1\2\3\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\41\uffff}>";
    static final String[] dfa_12s = {
            "\1\55\1\73\1\74\1\75\1\76\1\77\16\uffff\1\111\1\uffff\1\112\1\uffff\1\107\1\100\2\uffff\1\102\3\uffff\1\54\1\110\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7\10\uffff\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\12\uffff\1\104\3\uffff\1\103\1\105\1\106\1\101",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_7 = DFA.unpackEncodedString(dfa_7s);
    static final char[] dfa_8 = DFA.unpackEncodedStringToUnsignedChars(dfa_8s);
    static final char[] dfa_9 = DFA.unpackEncodedStringToUnsignedChars(dfa_9s);
    static final short[] dfa_10 = DFA.unpackEncodedString(dfa_10s);
    static final short[] dfa_11 = DFA.unpackEncodedString(dfa_11s);
    static final short[][] dfa_12 = unpackEncodedStringArray(dfa_12s);

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = dfa_7;
            this.eof = dfa_7;
            this.min = dfa_8;
            this.max = dfa_9;
            this.accept = dfa_10;
            this.special = dfa_11;
            this.transition = dfa_12;
        }
        public String getDescription() {
            return "1023:4: ( ( ( ruleS_Assignment )=>this_S_Assignment_1= ruleS_Assignment ) | this_S_1Expr_Facets_BlockOrEnd_2= ruleS_1Expr_Facets_BlockOrEnd | this_S_Other_3= ruleS_Other | this_S_Do_4= ruleS_Do | this_S_Return_5= ruleS_Return | this_S_Solve_6= ruleS_Solve | this_S_If_7= ruleS_If | this_S_Try_8= ruleS_Try | this_S_Equations_9= ruleS_Equations )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_0 = input.LA(1);

                         
                        int index18_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA18_0==38) && (synpred3_InternalGaml())) {s = 1;}

                        else if ( (LA18_0==39) && (synpred3_InternalGaml())) {s = 2;}

                        else if ( (LA18_0==77) ) {s = 3;}

                        else if ( (LA18_0==78) ) {s = 4;}

                        else if ( (LA18_0==79) && (synpred3_InternalGaml())) {s = 5;}

                        else if ( (LA18_0==80) && (synpred3_InternalGaml())) {s = 6;}

                        else if ( (LA18_0==81) && (synpred3_InternalGaml())) {s = 7;}

                        else if ( (LA18_0==67) ) {s = 8;}

                        else if ( (LA18_0==68) ) {s = 9;}

                        else if ( (LA18_0==69) ) {s = 10;}

                        else if ( (LA18_0==70) ) {s = 11;}

                        else if ( (LA18_0==71) ) {s = 12;}

                        else if ( (LA18_0==72) ) {s = 13;}

                        else if ( (LA18_0==73) ) {s = 14;}

                        else if ( (LA18_0==74) ) {s = 15;}

                        else if ( (LA18_0==75) ) {s = 16;}

                        else if ( (LA18_0==76) ) {s = 17;}

                        else if ( (LA18_0==41) ) {s = 18;}

                        else if ( (LA18_0==42) ) {s = 19;}

                        else if ( (LA18_0==43) ) {s = 20;}

                        else if ( (LA18_0==44) ) {s = 21;}

                        else if ( (LA18_0==45) ) {s = 22;}

                        else if ( (LA18_0==46) ) {s = 23;}

                        else if ( (LA18_0==47) ) {s = 24;}

                        else if ( (LA18_0==48) ) {s = 25;}

                        else if ( (LA18_0==49) ) {s = 26;}

                        else if ( (LA18_0==50) ) {s = 27;}

                        else if ( (LA18_0==51) ) {s = 28;}

                        else if ( (LA18_0==52) ) {s = 29;}

                        else if ( (LA18_0==53) ) {s = 30;}

                        else if ( (LA18_0==54) ) {s = 31;}

                        else if ( (LA18_0==55) ) {s = 32;}

                        else if ( (LA18_0==56) ) {s = 33;}

                        else if ( (LA18_0==57) ) {s = 34;}

                        else if ( (LA18_0==58) ) {s = 35;}

                        else if ( (LA18_0==59) ) {s = 36;}

                        else if ( (LA18_0==60) ) {s = 37;}

                        else if ( (LA18_0==61) ) {s = 38;}

                        else if ( (LA18_0==62) ) {s = 39;}

                        else if ( (LA18_0==63) ) {s = 40;}

                        else if ( (LA18_0==64) ) {s = 41;}

                        else if ( (LA18_0==65) ) {s = 42;}

                        else if ( (LA18_0==66) ) {s = 43;}

                        else if ( (LA18_0==36) ) {s = 44;}

                        else if ( (LA18_0==RULE_ID) ) {s = 45;}

                        else if ( (LA18_0==40) && (synpred3_InternalGaml())) {s = 46;}

                        else if ( (LA18_0==90) && (synpred3_InternalGaml())) {s = 47;}

                        else if ( (LA18_0==91) && (synpred3_InternalGaml())) {s = 48;}

                        else if ( (LA18_0==92) && (synpred3_InternalGaml())) {s = 49;}

                        else if ( (LA18_0==93) && (synpred3_InternalGaml())) {s = 50;}

                        else if ( (LA18_0==94) && (synpred3_InternalGaml())) {s = 51;}

                        else if ( (LA18_0==95) && (synpred3_InternalGaml())) {s = 52;}

                        else if ( (LA18_0==96) && (synpred3_InternalGaml())) {s = 53;}

                        else if ( (LA18_0==97) && (synpred3_InternalGaml())) {s = 54;}

                        else if ( (LA18_0==98) && (synpred3_InternalGaml())) {s = 55;}

                        else if ( (LA18_0==99) && (synpred3_InternalGaml())) {s = 56;}

                        else if ( (LA18_0==100) && (synpred3_InternalGaml())) {s = 57;}

                        else if ( (LA18_0==101) && (synpred3_InternalGaml())) {s = 58;}

                        else if ( (LA18_0==RULE_STRING) && (synpred3_InternalGaml())) {s = 59;}

                        else if ( (LA18_0==RULE_INTEGER) && (synpred3_InternalGaml())) {s = 60;}

                        else if ( (LA18_0==RULE_DOUBLE) && (synpred3_InternalGaml())) {s = 61;}

                        else if ( (LA18_0==RULE_BOOLEAN) && (synpred3_InternalGaml())) {s = 62;}

                        else if ( (LA18_0==RULE_KEYWORD) && (synpred3_InternalGaml())) {s = 63;}

                        else if ( (LA18_0==29) && (synpred3_InternalGaml())) {s = 64;}

                        else if ( (LA18_0==119) && (synpred3_InternalGaml())) {s = 65;}

                        else if ( (LA18_0==32) && (synpred3_InternalGaml())) {s = 66;}

                        else if ( (LA18_0==116) && (synpred3_InternalGaml())) {s = 67;}

                        else if ( (LA18_0==112) && (synpred3_InternalGaml())) {s = 68;}

                        else if ( (LA18_0==117) && (synpred3_InternalGaml())) {s = 69;}

                        else if ( (LA18_0==118) && (synpred3_InternalGaml())) {s = 70;}

                        else if ( (LA18_0==28) ) {s = 71;}

                        else if ( (LA18_0==37) ) {s = 72;}

                        else if ( (LA18_0==24) ) {s = 73;}

                        else if ( (LA18_0==26) ) {s = 74;}

                         
                        input.seek(index18_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA18_3 = input.LA(1);

                         
                        int index18_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 75;}

                         
                        input.seek(index18_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA18_4 = input.LA(1);

                         
                        int index18_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 75;}

                         
                        input.seek(index18_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA18_8 = input.LA(1);

                         
                        int index18_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA18_9 = input.LA(1);

                         
                        int index18_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA18_10 = input.LA(1);

                         
                        int index18_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA18_11 = input.LA(1);

                         
                        int index18_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA18_12 = input.LA(1);

                         
                        int index18_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_12);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA18_13 = input.LA(1);

                         
                        int index18_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_13);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA18_14 = input.LA(1);

                         
                        int index18_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_14);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA18_15 = input.LA(1);

                         
                        int index18_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_15);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA18_16 = input.LA(1);

                         
                        int index18_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_16);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA18_17 = input.LA(1);

                         
                        int index18_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_17);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA18_18 = input.LA(1);

                         
                        int index18_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_18);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA18_19 = input.LA(1);

                         
                        int index18_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_19);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA18_20 = input.LA(1);

                         
                        int index18_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_20);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA18_21 = input.LA(1);

                         
                        int index18_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA18_22 = input.LA(1);

                         
                        int index18_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_22);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA18_23 = input.LA(1);

                         
                        int index18_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_23);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA18_24 = input.LA(1);

                         
                        int index18_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA18_25 = input.LA(1);

                         
                        int index18_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_25);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA18_26 = input.LA(1);

                         
                        int index18_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_26);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA18_27 = input.LA(1);

                         
                        int index18_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_27);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA18_28 = input.LA(1);

                         
                        int index18_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_28);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA18_29 = input.LA(1);

                         
                        int index18_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_29);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA18_30 = input.LA(1);

                         
                        int index18_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_30);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA18_31 = input.LA(1);

                         
                        int index18_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_31);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA18_32 = input.LA(1);

                         
                        int index18_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_32);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA18_33 = input.LA(1);

                         
                        int index18_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_33);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA18_34 = input.LA(1);

                         
                        int index18_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_34);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA18_35 = input.LA(1);

                         
                        int index18_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_35);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA18_36 = input.LA(1);

                         
                        int index18_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_36);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA18_37 = input.LA(1);

                         
                        int index18_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_37);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA18_38 = input.LA(1);

                         
                        int index18_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_38);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA18_39 = input.LA(1);

                         
                        int index18_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_39);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA18_40 = input.LA(1);

                         
                        int index18_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_40);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA18_41 = input.LA(1);

                         
                        int index18_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_41);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA18_42 = input.LA(1);

                         
                        int index18_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_42);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA18_43 = input.LA(1);

                         
                        int index18_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 76;}

                         
                        input.seek(index18_43);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA18_44 = input.LA(1);

                         
                        int index18_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 77;}

                         
                        input.seek(index18_44);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA18_45 = input.LA(1);

                         
                        int index18_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_InternalGaml()) ) {s = 70;}

                        else if ( (true) ) {s = 78;}

                         
                        input.seek(index18_45);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_13s = "\70\uffff";
    static final String dfa_14s = "\3\4\65\uffff";
    static final String dfa_15s = "\1\121\2\156\65\uffff";
    static final String dfa_16s = "\3\uffff\1\2\1\3\1\4\1\5\1\6\60\1";
    static final String dfa_17s = "\1\uffff\1\1\1\0\65\uffff}>";
    static final String[] dfa_18s = {
            "\1\1\22\uffff\1\6\7\uffff\1\5\6\uffff\1\2\1\3\47\uffff\3\4",
            "\1\65\1\7\36\uffff\1\64\1\uffff\1\11\1\12\1\66\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57\1\60\1\61\1\62\1\63\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\13\1\14\1\15\1\16\1\17\34\uffff\1\10",
            "\1\3\151\uffff\1\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_13 = DFA.unpackEncodedString(dfa_13s);
    static final char[] dfa_14 = DFA.unpackEncodedStringToUnsignedChars(dfa_14s);
    static final char[] dfa_15 = DFA.unpackEncodedStringToUnsignedChars(dfa_15s);
    static final short[] dfa_16 = DFA.unpackEncodedString(dfa_16s);
    static final short[] dfa_17 = DFA.unpackEncodedString(dfa_17s);
    static final short[][] dfa_18 = unpackEncodedStringArray(dfa_18s);

    class DFA33 extends DFA {

        public DFA33(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 33;
            this.eot = dfa_13;
            this.eof = dfa_13;
            this.min = dfa_14;
            this.max = dfa_15;
            this.accept = dfa_16;
            this.special = dfa_17;
            this.transition = dfa_18;
        }
        public String getDescription() {
            return "1765:2: ( ( ( 'species' | RULE_ID )=>this_S_Definition_0= ruleS_Definition ) | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop | this_S_StringDefinition_5= ruleS_StringDefinition )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA33_2 = input.LA(1);

                         
                        int index33_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA33_2==110) && (synpred6_InternalGaml())) {s = 55;}

                        else if ( (LA33_2==RULE_ID) ) {s = 3;}

                         
                        input.seek(index33_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA33_1 = input.LA(1);

                         
                        int index33_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA33_1==RULE_STRING) ) {s = 7;}

                        else if ( (LA33_1==110) && (synpred6_InternalGaml())) {s = 8;}

                        else if ( (LA33_1==38) && (synpred6_InternalGaml())) {s = 9;}

                        else if ( (LA33_1==39) && (synpred6_InternalGaml())) {s = 10;}

                        else if ( (LA33_1==77) && (synpred6_InternalGaml())) {s = 11;}

                        else if ( (LA33_1==78) && (synpred6_InternalGaml())) {s = 12;}

                        else if ( (LA33_1==79) && (synpred6_InternalGaml())) {s = 13;}

                        else if ( (LA33_1==80) && (synpred6_InternalGaml())) {s = 14;}

                        else if ( (LA33_1==81) && (synpred6_InternalGaml())) {s = 15;}

                        else if ( (LA33_1==67) && (synpred6_InternalGaml())) {s = 16;}

                        else if ( (LA33_1==68) && (synpred6_InternalGaml())) {s = 17;}

                        else if ( (LA33_1==69) && (synpred6_InternalGaml())) {s = 18;}

                        else if ( (LA33_1==70) && (synpred6_InternalGaml())) {s = 19;}

                        else if ( (LA33_1==71) && (synpred6_InternalGaml())) {s = 20;}

                        else if ( (LA33_1==72) && (synpred6_InternalGaml())) {s = 21;}

                        else if ( (LA33_1==73) && (synpred6_InternalGaml())) {s = 22;}

                        else if ( (LA33_1==74) && (synpred6_InternalGaml())) {s = 23;}

                        else if ( (LA33_1==75) && (synpred6_InternalGaml())) {s = 24;}

                        else if ( (LA33_1==76) && (synpred6_InternalGaml())) {s = 25;}

                        else if ( (LA33_1==41) && (synpred6_InternalGaml())) {s = 26;}

                        else if ( (LA33_1==42) && (synpred6_InternalGaml())) {s = 27;}

                        else if ( (LA33_1==43) && (synpred6_InternalGaml())) {s = 28;}

                        else if ( (LA33_1==44) && (synpred6_InternalGaml())) {s = 29;}

                        else if ( (LA33_1==45) && (synpred6_InternalGaml())) {s = 30;}

                        else if ( (LA33_1==46) && (synpred6_InternalGaml())) {s = 31;}

                        else if ( (LA33_1==47) && (synpred6_InternalGaml())) {s = 32;}

                        else if ( (LA33_1==48) && (synpred6_InternalGaml())) {s = 33;}

                        else if ( (LA33_1==49) && (synpred6_InternalGaml())) {s = 34;}

                        else if ( (LA33_1==50) && (synpred6_InternalGaml())) {s = 35;}

                        else if ( (LA33_1==51) && (synpred6_InternalGaml())) {s = 36;}

                        else if ( (LA33_1==52) && (synpred6_InternalGaml())) {s = 37;}

                        else if ( (LA33_1==53) && (synpred6_InternalGaml())) {s = 38;}

                        else if ( (LA33_1==54) && (synpred6_InternalGaml())) {s = 39;}

                        else if ( (LA33_1==55) && (synpred6_InternalGaml())) {s = 40;}

                        else if ( (LA33_1==56) && (synpred6_InternalGaml())) {s = 41;}

                        else if ( (LA33_1==57) && (synpred6_InternalGaml())) {s = 42;}

                        else if ( (LA33_1==58) && (synpred6_InternalGaml())) {s = 43;}

                        else if ( (LA33_1==59) && (synpred6_InternalGaml())) {s = 44;}

                        else if ( (LA33_1==60) && (synpred6_InternalGaml())) {s = 45;}

                        else if ( (LA33_1==61) && (synpred6_InternalGaml())) {s = 46;}

                        else if ( (LA33_1==62) && (synpred6_InternalGaml())) {s = 47;}

                        else if ( (LA33_1==63) && (synpred6_InternalGaml())) {s = 48;}

                        else if ( (LA33_1==64) && (synpred6_InternalGaml())) {s = 49;}

                        else if ( (LA33_1==65) && (synpred6_InternalGaml())) {s = 50;}

                        else if ( (LA33_1==66) && (synpred6_InternalGaml())) {s = 51;}

                        else if ( (LA33_1==36) && (synpred6_InternalGaml())) {s = 52;}

                        else if ( (LA33_1==RULE_ID) && (synpred6_InternalGaml())) {s = 53;}

                        else if ( (LA33_1==40) && (synpred6_InternalGaml())) {s = 54;}

                         
                        input.seek(index33_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 33, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_19s = "\61\uffff";
    static final String dfa_20s = "\1\4\56\35\2\uffff";
    static final String dfa_21s = "\1\121\56\156\2\uffff";
    static final String dfa_22s = "\57\uffff\1\2\1\1";
    static final String dfa_23s = "\61\uffff}>";
    static final String[] dfa_24s = {
            "\1\55\37\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "\1\60\4\uffff\1\57\113\uffff\1\60",
            "",
            ""
    };

    static final short[] dfa_19 = DFA.unpackEncodedString(dfa_19s);
    static final char[] dfa_20 = DFA.unpackEncodedStringToUnsignedChars(dfa_20s);
    static final char[] dfa_21 = DFA.unpackEncodedStringToUnsignedChars(dfa_21s);
    static final short[] dfa_22 = DFA.unpackEncodedString(dfa_22s);
    static final short[] dfa_23 = DFA.unpackEncodedString(dfa_23s);
    static final short[][] dfa_24 = unpackEncodedStringArray(dfa_24s);

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = dfa_19;
            this.eof = dfa_19;
            this.min = dfa_20;
            this.max = dfa_21;
            this.accept = dfa_22;
            this.special = dfa_23;
            this.transition = dfa_24;
        }
        public String getDescription() {
            return "2526:5: (lv_expr_0_1= ruleFunction | lv_expr_0_2= ruleVariableRef )";
        }
    }
    static final String dfa_25s = "\12\uffff";
    static final String dfa_26s = "\1\17\2\uffff\1\123\6\uffff";
    static final String dfa_27s = "\1\127\2\uffff\1\125\6\uffff";
    static final String dfa_28s = "\1\uffff\1\1\1\2\1\uffff\1\4\1\6\1\7\1\10\1\5\1\3";
    static final String dfa_29s = "\12\uffff}>";
    static final String[] dfa_30s = {
            "\1\1\102\uffff\1\2\1\3\1\4\1\7\1\5\1\6",
            "",
            "",
            "\1\11\1\uffff\1\10",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_25 = DFA.unpackEncodedString(dfa_25s);
    static final char[] dfa_26 = DFA.unpackEncodedStringToUnsignedChars(dfa_26s);
    static final char[] dfa_27 = DFA.unpackEncodedStringToUnsignedChars(dfa_27s);
    static final short[] dfa_28 = DFA.unpackEncodedString(dfa_28s);
    static final short[] dfa_29 = DFA.unpackEncodedString(dfa_29s);
    static final short[][] dfa_30 = unpackEncodedStringArray(dfa_30s);

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = dfa_25;
            this.eof = dfa_25;
            this.min = dfa_26;
            this.max = dfa_27;
            this.accept = dfa_28;
            this.special = dfa_29;
            this.transition = dfa_30;
        }
        public String getDescription() {
            return "3437:2: (kw= '<-' | kw= '<<' | (kw= '>' kw= '>' ) | kw= '<<+' | (kw= '>' kw= '>-' ) | kw= '+<-' | kw= '<+' | kw= '>-' )";
        }
    }
    static final String dfa_31s = "\110\uffff";
    static final String dfa_32s = "\1\4\56\0\14\uffff\14\0\1\uffff";
    static final String dfa_33s = "\1\167\56\0\14\uffff\14\0\1\uffff";
    static final String dfa_34s = "\57\uffff\14\1\14\uffff\1\2";
    static final String dfa_35s = "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\14\uffff\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\1\uffff}>";
    static final String[] dfa_36s = {
            "\1\55\1\73\1\74\1\75\1\76\1\77\23\uffff\1\100\2\uffff\1\102\3\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7\10\uffff\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\12\uffff\1\104\3\uffff\1\103\1\105\1\106\1\101",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] dfa_31 = DFA.unpackEncodedString(dfa_31s);
    static final char[] dfa_32 = DFA.unpackEncodedStringToUnsignedChars(dfa_32s);
    static final char[] dfa_33 = DFA.unpackEncodedStringToUnsignedChars(dfa_33s);
    static final short[] dfa_34 = DFA.unpackEncodedString(dfa_34s);
    static final short[] dfa_35 = DFA.unpackEncodedString(dfa_35s);
    static final short[][] dfa_36 = unpackEncodedStringArray(dfa_36s);

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = dfa_31;
            this.eof = dfa_31;
            this.min = dfa_32;
            this.max = dfa_33;
            this.accept = dfa_34;
            this.special = dfa_35;
            this.transition = dfa_36;
        }
        public String getDescription() {
            return "4180:2: ( ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=>this_ArgumentPair_0= ruleArgumentPair ) | this_Pair_1= rulePair )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA73_0 = input.LA(1);

                         
                        int index73_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA73_0==38) ) {s = 1;}

                        else if ( (LA73_0==39) ) {s = 2;}

                        else if ( (LA73_0==77) ) {s = 3;}

                        else if ( (LA73_0==78) ) {s = 4;}

                        else if ( (LA73_0==79) ) {s = 5;}

                        else if ( (LA73_0==80) ) {s = 6;}

                        else if ( (LA73_0==81) ) {s = 7;}

                        else if ( (LA73_0==67) ) {s = 8;}

                        else if ( (LA73_0==68) ) {s = 9;}

                        else if ( (LA73_0==69) ) {s = 10;}

                        else if ( (LA73_0==70) ) {s = 11;}

                        else if ( (LA73_0==71) ) {s = 12;}

                        else if ( (LA73_0==72) ) {s = 13;}

                        else if ( (LA73_0==73) ) {s = 14;}

                        else if ( (LA73_0==74) ) {s = 15;}

                        else if ( (LA73_0==75) ) {s = 16;}

                        else if ( (LA73_0==76) ) {s = 17;}

                        else if ( (LA73_0==41) ) {s = 18;}

                        else if ( (LA73_0==42) ) {s = 19;}

                        else if ( (LA73_0==43) ) {s = 20;}

                        else if ( (LA73_0==44) ) {s = 21;}

                        else if ( (LA73_0==45) ) {s = 22;}

                        else if ( (LA73_0==46) ) {s = 23;}

                        else if ( (LA73_0==47) ) {s = 24;}

                        else if ( (LA73_0==48) ) {s = 25;}

                        else if ( (LA73_0==49) ) {s = 26;}

                        else if ( (LA73_0==50) ) {s = 27;}

                        else if ( (LA73_0==51) ) {s = 28;}

                        else if ( (LA73_0==52) ) {s = 29;}

                        else if ( (LA73_0==53) ) {s = 30;}

                        else if ( (LA73_0==54) ) {s = 31;}

                        else if ( (LA73_0==55) ) {s = 32;}

                        else if ( (LA73_0==56) ) {s = 33;}

                        else if ( (LA73_0==57) ) {s = 34;}

                        else if ( (LA73_0==58) ) {s = 35;}

                        else if ( (LA73_0==59) ) {s = 36;}

                        else if ( (LA73_0==60) ) {s = 37;}

                        else if ( (LA73_0==61) ) {s = 38;}

                        else if ( (LA73_0==62) ) {s = 39;}

                        else if ( (LA73_0==63) ) {s = 40;}

                        else if ( (LA73_0==64) ) {s = 41;}

                        else if ( (LA73_0==65) ) {s = 42;}

                        else if ( (LA73_0==66) ) {s = 43;}

                        else if ( (LA73_0==36) ) {s = 44;}

                        else if ( (LA73_0==RULE_ID) ) {s = 45;}

                        else if ( (LA73_0==40) ) {s = 46;}

                        else if ( (LA73_0==90) && (synpred8_InternalGaml())) {s = 47;}

                        else if ( (LA73_0==91) && (synpred8_InternalGaml())) {s = 48;}

                        else if ( (LA73_0==92) && (synpred8_InternalGaml())) {s = 49;}

                        else if ( (LA73_0==93) && (synpred8_InternalGaml())) {s = 50;}

                        else if ( (LA73_0==94) && (synpred8_InternalGaml())) {s = 51;}

                        else if ( (LA73_0==95) && (synpred8_InternalGaml())) {s = 52;}

                        else if ( (LA73_0==96) && (synpred8_InternalGaml())) {s = 53;}

                        else if ( (LA73_0==97) && (synpred8_InternalGaml())) {s = 54;}

                        else if ( (LA73_0==98) && (synpred8_InternalGaml())) {s = 55;}

                        else if ( (LA73_0==99) && (synpred8_InternalGaml())) {s = 56;}

                        else if ( (LA73_0==100) && (synpred8_InternalGaml())) {s = 57;}

                        else if ( (LA73_0==101) && (synpred8_InternalGaml())) {s = 58;}

                        else if ( (LA73_0==RULE_STRING) ) {s = 59;}

                        else if ( (LA73_0==RULE_INTEGER) ) {s = 60;}

                        else if ( (LA73_0==RULE_DOUBLE) ) {s = 61;}

                        else if ( (LA73_0==RULE_BOOLEAN) ) {s = 62;}

                        else if ( (LA73_0==RULE_KEYWORD) ) {s = 63;}

                        else if ( (LA73_0==29) ) {s = 64;}

                        else if ( (LA73_0==119) ) {s = 65;}

                        else if ( (LA73_0==32) ) {s = 66;}

                        else if ( (LA73_0==116) ) {s = 67;}

                        else if ( (LA73_0==112) ) {s = 68;}

                        else if ( (LA73_0==117) ) {s = 69;}

                        else if ( (LA73_0==118) ) {s = 70;}

                         
                        input.seek(index73_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA73_1 = input.LA(1);

                         
                        int index73_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA73_2 = input.LA(1);

                         
                        int index73_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA73_3 = input.LA(1);

                         
                        int index73_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA73_4 = input.LA(1);

                         
                        int index73_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA73_5 = input.LA(1);

                         
                        int index73_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA73_6 = input.LA(1);

                         
                        int index73_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA73_7 = input.LA(1);

                         
                        int index73_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA73_8 = input.LA(1);

                         
                        int index73_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA73_9 = input.LA(1);

                         
                        int index73_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA73_10 = input.LA(1);

                         
                        int index73_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA73_11 = input.LA(1);

                         
                        int index73_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA73_12 = input.LA(1);

                         
                        int index73_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA73_13 = input.LA(1);

                         
                        int index73_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA73_14 = input.LA(1);

                         
                        int index73_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA73_15 = input.LA(1);

                         
                        int index73_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA73_16 = input.LA(1);

                         
                        int index73_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA73_17 = input.LA(1);

                         
                        int index73_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA73_18 = input.LA(1);

                         
                        int index73_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA73_19 = input.LA(1);

                         
                        int index73_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA73_20 = input.LA(1);

                         
                        int index73_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA73_21 = input.LA(1);

                         
                        int index73_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA73_22 = input.LA(1);

                         
                        int index73_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA73_23 = input.LA(1);

                         
                        int index73_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA73_24 = input.LA(1);

                         
                        int index73_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA73_25 = input.LA(1);

                         
                        int index73_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_25);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA73_26 = input.LA(1);

                         
                        int index73_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_26);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA73_27 = input.LA(1);

                         
                        int index73_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_27);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA73_28 = input.LA(1);

                         
                        int index73_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_28);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA73_29 = input.LA(1);

                         
                        int index73_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_29);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA73_30 = input.LA(1);

                         
                        int index73_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_30);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA73_31 = input.LA(1);

                         
                        int index73_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_31);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA73_32 = input.LA(1);

                         
                        int index73_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_32);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA73_33 = input.LA(1);

                         
                        int index73_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_33);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA73_34 = input.LA(1);

                         
                        int index73_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_34);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA73_35 = input.LA(1);

                         
                        int index73_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_35);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA73_36 = input.LA(1);

                         
                        int index73_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_36);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA73_37 = input.LA(1);

                         
                        int index73_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_37);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA73_38 = input.LA(1);

                         
                        int index73_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_38);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA73_39 = input.LA(1);

                         
                        int index73_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_39);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA73_40 = input.LA(1);

                         
                        int index73_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_40);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA73_41 = input.LA(1);

                         
                        int index73_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_41);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA73_42 = input.LA(1);

                         
                        int index73_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_42);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA73_43 = input.LA(1);

                         
                        int index73_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_43);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA73_44 = input.LA(1);

                         
                        int index73_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_44);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA73_45 = input.LA(1);

                         
                        int index73_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_45);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA73_46 = input.LA(1);

                         
                        int index73_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_46);
                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA73_59 = input.LA(1);

                         
                        int index73_59 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_59);
                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA73_60 = input.LA(1);

                         
                        int index73_60 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_60);
                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA73_61 = input.LA(1);

                         
                        int index73_61 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_61);
                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA73_62 = input.LA(1);

                         
                        int index73_62 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_62);
                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA73_63 = input.LA(1);

                         
                        int index73_63 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_63);
                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA73_64 = input.LA(1);

                         
                        int index73_64 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_64);
                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA73_65 = input.LA(1);

                         
                        int index73_65 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_65);
                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA73_66 = input.LA(1);

                         
                        int index73_66 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_66);
                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA73_67 = input.LA(1);

                         
                        int index73_67 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_67);
                        if ( s>=0 ) return s;
                        break;
                    case 56 : 
                        int LA73_68 = input.LA(1);

                         
                        int index73_68 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_68);
                        if ( s>=0 ) return s;
                        break;
                    case 57 : 
                        int LA73_69 = input.LA(1);

                         
                        int index73_69 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_69);
                        if ( s>=0 ) return s;
                        break;
                    case 58 : 
                        int LA73_70 = input.LA(1);

                         
                        int index73_70 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 71;}

                         
                        input.seek(index73_70);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 73, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_37s = "\107\uffff";
    static final String dfa_38s = "\1\4\56\0\30\uffff";
    static final String dfa_39s = "\1\167\56\0\30\uffff";
    static final String dfa_40s = "\57\uffff\14\1\1\2\13\uffff";
    static final String dfa_41s = "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56\30\uffff}>";
    static final String[] dfa_42s = {
            "\1\55\5\73\23\uffff\1\73\2\uffff\1\73\3\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7\10\uffff\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\12\uffff\1\73\3\uffff\4\73",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_37 = DFA.unpackEncodedString(dfa_37s);
    static final char[] dfa_38 = DFA.unpackEncodedStringToUnsignedChars(dfa_38s);
    static final char[] dfa_39 = DFA.unpackEncodedStringToUnsignedChars(dfa_39s);
    static final short[] dfa_40 = DFA.unpackEncodedString(dfa_40s);
    static final short[] dfa_41 = DFA.unpackEncodedString(dfa_41s);
    static final short[][] dfa_42 = unpackEncodedStringArray(dfa_42s);

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = dfa_37;
            this.eof = dfa_37;
            this.min = dfa_38;
            this.max = dfa_39;
            this.accept = dfa_40;
            this.special = dfa_41;
            this.transition = dfa_42;
        }
        public String getDescription() {
            return "4243:3: ( ( ( ( ( ( ruleValid_ID ) ) '::' ) | ( ( ( ( ruleDefinitionFacetKey | ruleSpecialFacetKey | ruleActionFacetKey ) ) ) ':' ) ) )=> ( ( ( (lv_op_0_0= ruleValid_ID ) ) otherlv_1= '::' ) | ( ( ( (lv_op_2_1= ruleDefinitionFacetKey | lv_op_2_2= ruleSpecialFacetKey | lv_op_2_3= ruleActionFacetKey ) ) ) otherlv_3= ':' ) ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA76_0 = input.LA(1);

                         
                        int index76_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_0==38) ) {s = 1;}

                        else if ( (LA76_0==39) ) {s = 2;}

                        else if ( (LA76_0==77) ) {s = 3;}

                        else if ( (LA76_0==78) ) {s = 4;}

                        else if ( (LA76_0==79) ) {s = 5;}

                        else if ( (LA76_0==80) ) {s = 6;}

                        else if ( (LA76_0==81) ) {s = 7;}

                        else if ( (LA76_0==67) ) {s = 8;}

                        else if ( (LA76_0==68) ) {s = 9;}

                        else if ( (LA76_0==69) ) {s = 10;}

                        else if ( (LA76_0==70) ) {s = 11;}

                        else if ( (LA76_0==71) ) {s = 12;}

                        else if ( (LA76_0==72) ) {s = 13;}

                        else if ( (LA76_0==73) ) {s = 14;}

                        else if ( (LA76_0==74) ) {s = 15;}

                        else if ( (LA76_0==75) ) {s = 16;}

                        else if ( (LA76_0==76) ) {s = 17;}

                        else if ( (LA76_0==41) ) {s = 18;}

                        else if ( (LA76_0==42) ) {s = 19;}

                        else if ( (LA76_0==43) ) {s = 20;}

                        else if ( (LA76_0==44) ) {s = 21;}

                        else if ( (LA76_0==45) ) {s = 22;}

                        else if ( (LA76_0==46) ) {s = 23;}

                        else if ( (LA76_0==47) ) {s = 24;}

                        else if ( (LA76_0==48) ) {s = 25;}

                        else if ( (LA76_0==49) ) {s = 26;}

                        else if ( (LA76_0==50) ) {s = 27;}

                        else if ( (LA76_0==51) ) {s = 28;}

                        else if ( (LA76_0==52) ) {s = 29;}

                        else if ( (LA76_0==53) ) {s = 30;}

                        else if ( (LA76_0==54) ) {s = 31;}

                        else if ( (LA76_0==55) ) {s = 32;}

                        else if ( (LA76_0==56) ) {s = 33;}

                        else if ( (LA76_0==57) ) {s = 34;}

                        else if ( (LA76_0==58) ) {s = 35;}

                        else if ( (LA76_0==59) ) {s = 36;}

                        else if ( (LA76_0==60) ) {s = 37;}

                        else if ( (LA76_0==61) ) {s = 38;}

                        else if ( (LA76_0==62) ) {s = 39;}

                        else if ( (LA76_0==63) ) {s = 40;}

                        else if ( (LA76_0==64) ) {s = 41;}

                        else if ( (LA76_0==65) ) {s = 42;}

                        else if ( (LA76_0==66) ) {s = 43;}

                        else if ( (LA76_0==36) ) {s = 44;}

                        else if ( (LA76_0==RULE_ID) ) {s = 45;}

                        else if ( (LA76_0==40) ) {s = 46;}

                        else if ( (LA76_0==90) && (synpred9_InternalGaml())) {s = 47;}

                        else if ( (LA76_0==91) && (synpred9_InternalGaml())) {s = 48;}

                        else if ( (LA76_0==92) && (synpred9_InternalGaml())) {s = 49;}

                        else if ( (LA76_0==93) && (synpred9_InternalGaml())) {s = 50;}

                        else if ( (LA76_0==94) && (synpred9_InternalGaml())) {s = 51;}

                        else if ( (LA76_0==95) && (synpred9_InternalGaml())) {s = 52;}

                        else if ( (LA76_0==96) && (synpred9_InternalGaml())) {s = 53;}

                        else if ( (LA76_0==97) && (synpred9_InternalGaml())) {s = 54;}

                        else if ( (LA76_0==98) && (synpred9_InternalGaml())) {s = 55;}

                        else if ( (LA76_0==99) && (synpred9_InternalGaml())) {s = 56;}

                        else if ( (LA76_0==100) && (synpred9_InternalGaml())) {s = 57;}

                        else if ( (LA76_0==101) && (synpred9_InternalGaml())) {s = 58;}

                        else if ( ((LA76_0>=RULE_STRING && LA76_0<=RULE_KEYWORD)||LA76_0==29||LA76_0==32||LA76_0==112||(LA76_0>=116 && LA76_0<=119)) ) {s = 59;}

                         
                        input.seek(index76_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA76_1 = input.LA(1);

                         
                        int index76_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA76_2 = input.LA(1);

                         
                        int index76_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA76_3 = input.LA(1);

                         
                        int index76_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA76_4 = input.LA(1);

                         
                        int index76_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA76_5 = input.LA(1);

                         
                        int index76_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA76_6 = input.LA(1);

                         
                        int index76_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA76_7 = input.LA(1);

                         
                        int index76_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA76_8 = input.LA(1);

                         
                        int index76_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA76_9 = input.LA(1);

                         
                        int index76_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA76_10 = input.LA(1);

                         
                        int index76_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA76_11 = input.LA(1);

                         
                        int index76_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA76_12 = input.LA(1);

                         
                        int index76_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA76_13 = input.LA(1);

                         
                        int index76_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA76_14 = input.LA(1);

                         
                        int index76_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA76_15 = input.LA(1);

                         
                        int index76_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA76_16 = input.LA(1);

                         
                        int index76_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA76_17 = input.LA(1);

                         
                        int index76_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA76_18 = input.LA(1);

                         
                        int index76_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA76_19 = input.LA(1);

                         
                        int index76_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA76_20 = input.LA(1);

                         
                        int index76_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA76_21 = input.LA(1);

                         
                        int index76_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA76_22 = input.LA(1);

                         
                        int index76_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA76_23 = input.LA(1);

                         
                        int index76_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA76_24 = input.LA(1);

                         
                        int index76_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA76_25 = input.LA(1);

                         
                        int index76_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_25);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA76_26 = input.LA(1);

                         
                        int index76_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_26);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA76_27 = input.LA(1);

                         
                        int index76_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_27);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA76_28 = input.LA(1);

                         
                        int index76_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_28);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA76_29 = input.LA(1);

                         
                        int index76_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_29);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA76_30 = input.LA(1);

                         
                        int index76_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_30);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA76_31 = input.LA(1);

                         
                        int index76_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_31);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA76_32 = input.LA(1);

                         
                        int index76_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_32);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA76_33 = input.LA(1);

                         
                        int index76_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_33);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA76_34 = input.LA(1);

                         
                        int index76_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_34);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA76_35 = input.LA(1);

                         
                        int index76_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_35);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA76_36 = input.LA(1);

                         
                        int index76_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_36);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA76_37 = input.LA(1);

                         
                        int index76_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_37);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA76_38 = input.LA(1);

                         
                        int index76_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_38);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA76_39 = input.LA(1);

                         
                        int index76_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_39);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA76_40 = input.LA(1);

                         
                        int index76_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_40);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA76_41 = input.LA(1);

                         
                        int index76_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_41);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA76_42 = input.LA(1);

                         
                        int index76_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_42);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA76_43 = input.LA(1);

                         
                        int index76_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_43);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA76_44 = input.LA(1);

                         
                        int index76_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_44);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA76_45 = input.LA(1);

                         
                        int index76_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_45);
                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA76_46 = input.LA(1);

                         
                        int index76_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_InternalGaml()) ) {s = 58;}

                        else if ( (true) ) {s = 59;}

                         
                        input.seek(index76_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 76, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_43s = "\1\4\56\0\2\uffff";
    static final String dfa_44s = "\1\121\56\0\2\uffff";
    static final String dfa_45s = "\57\uffff\1\1\1\2";
    static final String dfa_46s = "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\54\1\55\2\uffff}>";
    static final String[] dfa_47s = {
            "\1\55\37\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };
    static final char[] dfa_43 = DFA.unpackEncodedStringToUnsignedChars(dfa_43s);
    static final char[] dfa_44 = DFA.unpackEncodedStringToUnsignedChars(dfa_44s);
    static final short[] dfa_45 = DFA.unpackEncodedString(dfa_45s);
    static final short[] dfa_46 = DFA.unpackEncodedString(dfa_46s);
    static final short[][] dfa_47 = unpackEncodedStringArray(dfa_47s);

    class DFA102 extends DFA {

        public DFA102(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 102;
            this.eot = dfa_19;
            this.eof = dfa_19;
            this.min = dfa_43;
            this.max = dfa_44;
            this.accept = dfa_45;
            this.special = dfa_46;
            this.transition = dfa_47;
        }
        public String getDescription() {
            return "5763:2: ( ( ( ruleFunction )=>this_Function_0= ruleFunction ) | this_VariableRef_1= ruleVariableRef )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA102_1 = input.LA(1);

                         
                        int index102_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA102_2 = input.LA(1);

                         
                        int index102_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA102_3 = input.LA(1);

                         
                        int index102_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA102_4 = input.LA(1);

                         
                        int index102_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA102_5 = input.LA(1);

                         
                        int index102_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA102_6 = input.LA(1);

                         
                        int index102_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA102_7 = input.LA(1);

                         
                        int index102_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA102_8 = input.LA(1);

                         
                        int index102_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA102_9 = input.LA(1);

                         
                        int index102_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA102_10 = input.LA(1);

                         
                        int index102_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA102_11 = input.LA(1);

                         
                        int index102_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA102_12 = input.LA(1);

                         
                        int index102_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA102_13 = input.LA(1);

                         
                        int index102_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA102_14 = input.LA(1);

                         
                        int index102_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA102_15 = input.LA(1);

                         
                        int index102_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA102_16 = input.LA(1);

                         
                        int index102_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_16);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA102_17 = input.LA(1);

                         
                        int index102_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_17);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA102_18 = input.LA(1);

                         
                        int index102_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA102_19 = input.LA(1);

                         
                        int index102_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_19);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA102_20 = input.LA(1);

                         
                        int index102_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_20);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA102_21 = input.LA(1);

                         
                        int index102_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_21);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA102_22 = input.LA(1);

                         
                        int index102_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA102_23 = input.LA(1);

                         
                        int index102_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_23);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA102_24 = input.LA(1);

                         
                        int index102_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_24);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA102_25 = input.LA(1);

                         
                        int index102_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_25);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA102_26 = input.LA(1);

                         
                        int index102_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_26);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA102_27 = input.LA(1);

                         
                        int index102_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_27);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA102_28 = input.LA(1);

                         
                        int index102_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_28);
                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA102_29 = input.LA(1);

                         
                        int index102_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_29);
                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA102_30 = input.LA(1);

                         
                        int index102_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_30);
                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA102_31 = input.LA(1);

                         
                        int index102_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_31);
                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA102_32 = input.LA(1);

                         
                        int index102_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_32);
                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA102_33 = input.LA(1);

                         
                        int index102_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_33);
                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA102_34 = input.LA(1);

                         
                        int index102_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_34);
                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA102_35 = input.LA(1);

                         
                        int index102_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_35);
                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA102_36 = input.LA(1);

                         
                        int index102_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_36);
                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA102_37 = input.LA(1);

                         
                        int index102_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_37);
                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA102_38 = input.LA(1);

                         
                        int index102_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_38);
                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA102_39 = input.LA(1);

                         
                        int index102_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_39);
                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA102_40 = input.LA(1);

                         
                        int index102_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_40);
                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA102_41 = input.LA(1);

                         
                        int index102_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_41);
                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA102_42 = input.LA(1);

                         
                        int index102_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_42);
                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA102_43 = input.LA(1);

                         
                        int index102_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_43);
                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA102_44 = input.LA(1);

                         
                        int index102_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_44);
                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA102_45 = input.LA(1);

                         
                        int index102_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_45);
                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA102_46 = input.LA(1);

                         
                        int index102_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_InternalGaml()) ) {s = 47;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index102_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 102, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String dfa_48s = "\75\uffff";
    static final String dfa_49s = "\1\uffff\56\73\16\uffff";
    static final String dfa_50s = "\73\4\2\uffff";
    static final String dfa_51s = "\1\167\56\171\14\167\2\uffff";
    static final String dfa_52s = "\73\uffff\1\1\1\2";
    static final String dfa_53s = "\75\uffff}>";
    static final String[] dfa_54s = {
            "\1\55\5\73\23\uffff\1\73\2\uffff\1\73\3\uffff\1\54\1\uffff\1\1\1\2\1\56\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\3\1\4\1\5\1\6\1\7\10\uffff\1\57\1\60\1\61\1\62\1\63\1\64\1\65\1\66\1\67\1\70\1\71\1\72\12\uffff\1\73\3\uffff\4\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\1\73\15\uffff\1\73\12\uffff\2\73\3\uffff\1\73\1\uffff\1\73\1\uffff\54\73\1\uffff\1\73\4\uffff\1\73\1\74\15\uffff\16\73\2\uffff\3\73",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "\6\74\23\uffff\1\74\2\uffff\1\74\3\uffff\1\74\1\uffff\54\74\7\uffff\1\73\14\74\12\uffff\1\74\3\uffff\4\74",
            "",
            ""
    };

    static final short[] dfa_48 = DFA.unpackEncodedString(dfa_48s);
    static final short[] dfa_49 = DFA.unpackEncodedString(dfa_49s);
    static final char[] dfa_50 = DFA.unpackEncodedStringToUnsignedChars(dfa_50s);
    static final char[] dfa_51 = DFA.unpackEncodedStringToUnsignedChars(dfa_51s);
    static final short[] dfa_52 = DFA.unpackEncodedString(dfa_52s);
    static final short[] dfa_53 = DFA.unpackEncodedString(dfa_53s);
    static final short[][] dfa_54 = unpackEncodedStringArray(dfa_54s);

    class DFA107 extends DFA {

        public DFA107(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 107;
            this.eot = dfa_48;
            this.eof = dfa_49;
            this.min = dfa_50;
            this.max = dfa_51;
            this.accept = dfa_52;
            this.special = dfa_53;
            this.transition = dfa_54;
        }
        public String getDescription() {
            return "5893:2: ( ( ( (lv_exprs_0_0= ruleExpression ) ) (otherlv_1= ',' ( (lv_exprs_2_0= ruleExpression ) ) )* ) | ( ( (lv_exprs_3_0= ruleParameter ) ) (otherlv_4= ',' ( (lv_exprs_5_0= ruleParameter ) ) )* ) )";
        }
    }
    static final String dfa_55s = "\17\uffff";
    static final String dfa_56s = "\1\4\2\0\14\uffff";
    static final String dfa_57s = "\1\176\2\0\14\uffff";
    static final String dfa_58s = "\3\uffff\6\1\1\2\5\uffff";
    static final String dfa_59s = "\1\0\1\1\1\2\14\uffff}>";
    static final String[] dfa_60s = {
            "\1\1\13\uffff\2\11\1\uffff\1\11\3\uffff\1\10\7\uffff\1\7\6\uffff\1\2\1\3\1\11\46\uffff\1\4\1\5\1\6\10\uffff\1\11\43\uffff\1\11",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] dfa_55 = DFA.unpackEncodedString(dfa_55s);
    static final char[] dfa_56 = DFA.unpackEncodedStringToUnsignedChars(dfa_56s);
    static final char[] dfa_57 = DFA.unpackEncodedStringToUnsignedChars(dfa_57s);
    static final short[] dfa_58 = DFA.unpackEncodedString(dfa_58s);
    static final short[] dfa_59 = DFA.unpackEncodedString(dfa_59s);
    static final short[][] dfa_60 = unpackEncodedStringArray(dfa_60s);

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = dfa_55;
            this.eof = dfa_55;
            this.min = dfa_56;
            this.max = dfa_57;
            this.accept = dfa_58;
            this.special = dfa_59;
            this.transition = dfa_60;
        }
        public String getDescription() {
            return "6539:2: ( ( ( ( ruleS_Definition | ruleS_Species | ruleS_Reflex | ruleS_Action | ruleS_Loop ) )=> (this_S_Definition_0= ruleS_Definition | this_S_Species_1= ruleS_Species | this_S_Reflex_2= ruleS_Reflex | this_S_Action_3= ruleS_Action | this_S_Loop_4= ruleS_Loop ) ) | (this_Model_5= ruleModel | this_ArgumentDefinition_6= ruleArgumentDefinition | this_DefinitionFacet_7= ruleDefinitionFacet | this_VarFakeDefinition_8= ruleVarFakeDefinition | this_Import_9= ruleImport | this_S_Experiment_10= ruleS_Experiment ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA117_0 = input.LA(1);

                         
                        int index117_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA117_0==RULE_ID) ) {s = 1;}

                        else if ( (LA117_0==38) ) {s = 2;}

                        else if ( (LA117_0==39) && (synpred12_InternalGaml())) {s = 3;}

                        else if ( (LA117_0==79) && (synpred12_InternalGaml())) {s = 4;}

                        else if ( (LA117_0==80) && (synpred12_InternalGaml())) {s = 5;}

                        else if ( (LA117_0==81) && (synpred12_InternalGaml())) {s = 6;}

                        else if ( (LA117_0==31) && (synpred12_InternalGaml())) {s = 7;}

                        else if ( (LA117_0==23) && (synpred12_InternalGaml())) {s = 8;}

                        else if ( ((LA117_0>=16 && LA117_0<=17)||LA117_0==19||LA117_0==40||LA117_0==90||LA117_0==126) ) {s = 9;}

                         
                        input.seek(index117_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA117_1 = input.LA(1);

                         
                        int index117_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred12_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index117_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA117_2 = input.LA(1);

                         
                        int index117_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred12_InternalGaml()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index117_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 117, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0xFFFFFFD1200003F0L,0x00F1003FFC03FFFFL});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000090000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0xFFFFFFD000420010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0xFFFFFFD000400012L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0xFFFFFFD000000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0xFFFFFFD000000030L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0xFFFFFFD100308010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0xFFFFFFD100208010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0xFFFFFFD100008010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000101000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0xFFFFFFD1202003F0L,0x00F1003FFC03FFFFL});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0xFFFFFFD120208010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000004000000010L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000008000L,0x0000000000FC0000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0xFFFFFFD000208010L,0x0000007FFC03FFFFL});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0xFFFFFFD200000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0xFFFFFEC200000000L,0x0000000000001FFFL});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0xFFFFFFD100000010L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0xFFFFFFFBB58003F0L,0x00F1003FFC03FFFFL});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000002L,0x0000040000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000004020000010L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000400000002L,0x0000780000080000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000002L,0x0001800000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000002L,0x0006000000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0008000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0xFFFFFFD000000012L,0x000000000003FFFFL});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000002L,0x0280000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0xFFFFFFD1200003F0L,0x01F1003FFC03FFFFL});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000200000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000020000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0xFFFFFFD1600003F0L,0x00F1003FFC03FFFFL});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000001080000L});

}